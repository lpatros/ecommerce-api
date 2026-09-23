package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.product.ProductFilter;
import com.lpatros.ecommerce_api.dto.product.ProductPatch;
import com.lpatros.ecommerce_api.dto.product.ProductRequest;
import com.lpatros.ecommerce_api.dto.product.ProductResponse;
import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.entity.Product;
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.mapper.ProductMapper;
import com.lpatros.ecommerce_api.repository.CategoryRepository;
import com.lpatros.ecommerce_api.repository.ProductRepository;
import com.lpatros.ecommerce_api.validator.ProductValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock private ProductValidator productValidator;
    @Mock private ProductRepository productRepository;
    @Mock private CategoryRepository categoryRepository;
    @Mock private ProductMapper productMapper;

    @InjectMocks private ProductService productService;

    private Product product(Long id) {
        Product p = new Product();
        p.setId(id);
        p.setName("Widget");
        p.setStock(10);
        p.setPrice(BigDecimal.TEN);
        p.setImageUrl("img.png");
        p.setDeleted(false);
        return p;
    }

    private Category category() {
        return new Category(1L, "Cat", false);
    }

    @Test
    void findById_returnsMappedResponse() {
        Product p = product(1L);
        ProductResponse expected = new ProductResponse();
        when(productRepository.findById(1L)).thenReturn(Optional.of(p));
        when(productMapper.toResponse(p)).thenReturn(expected);
        assertThat(productService.findById(1L)).isEqualTo(expected);
    }

    @Test
    void findById_throwsNotFound_whenMissing() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> productService.findById(99L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void create_throwsNotFound_whenCategoryMissing() {
        ProductRequest request = new ProductRequest();
        request.setCategoryId(5L);
        when(categoryRepository.findById(5L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> productService.create(request))
                .isInstanceOf(NotFoundException.class);
        verify(productValidator, never()).validateCreate(any());
    }

    @Test
    void create_savesProduct_whenValid() {
        ProductRequest request = new ProductRequest();
        request.setCategoryId(1L);
        Category cat = category();
        Product p = product(null);
        ProductResponse response = new ProductResponse();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(cat));
        when(productMapper.toEntity(request, cat)).thenReturn(p);
        when(productRepository.save(p)).thenReturn(p);
        when(productMapper.toResponse(p)).thenReturn(response);
        assertThat(productService.create(request)).isEqualTo(response);
        verify(productValidator).validateCreate(request);
    }

    @Test
    void update_throwsNotFound_whenProductMissing() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> productService.update(1L, new ProductRequest()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void delete_callsRepository_whenExists() {
        Product p = product(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(p));
        productService.delete(1L);
        verify(productRepository).deleteById(1L);
    }

    @Test
    void delete_throwsNotFound_whenMissing() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> productService.delete(1L))
                .isInstanceOf(NotFoundException.class);
        verify(productRepository, never()).deleteById(anyLong());
    }

    @Test
    void findAll_returnsMappedPagination() {
        Product p = product(1L);
        ProductResponse pr = new ProductResponse();
        Page<Product> page = new PageImpl<>(List.of(p), PageRequest.of(0, 10), 1);
        when(productRepository.findAll(any(org.springframework.data.jpa.domain.Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(page);
        when(productMapper.toResponsePagination(page)).thenReturn(new Pagination<>(page.map(x -> pr)));
        Pagination<ProductResponse> result = productService.findAll(new ProductFilter(), PageRequest.of(0, 10));
        assertThat(result.getContent()).containsExactly(pr);
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    @Test
    void partialUpdate_throwsNotFound_whenProductMissing() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> productService.partialUpdate(1L, new ProductPatch()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void update_throwsNotFound_whenCategoryMissing() {
        ProductRequest request = new ProductRequest();
        request.setCategoryId(5L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product(1L)));
        when(categoryRepository.findById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.update(1L, request))
                .isInstanceOf(NotFoundException.class);
        verify(productValidator, never()).validateUpdate(any(), anyLong());
    }

    @Test
    void update_savesProduct_whenValid() {
        Product existing = product(1L);
        existing.setCreatedAt(java.time.LocalDateTime.of(2020, 1, 1, 0, 0));
        ProductRequest request = new ProductRequest();
        request.setCategoryId(1L);
        Category cat = category();
        Product mapped = product(null);
        ProductResponse expected = new ProductResponse();

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(cat));
        when(productMapper.toEntity(request, cat)).thenReturn(mapped);
        when(productRepository.save(mapped)).thenReturn(mapped);
        when(productMapper.toResponse(mapped)).thenReturn(expected);

        assertThat(productService.update(1L, request)).isEqualTo(expected);
        assertThat(mapped.getId()).isEqualTo(1L);
        assertThat(mapped.getCreatedAt()).isEqualTo(existing.getCreatedAt());
        verify(productValidator).validateUpdate(request, 1L);
    }

    @Test
    void partialUpdate_throwsNotFound_whenCategoryMissing() {
        ProductPatch patch = new ProductPatch();
        patch.setCategoryId(5L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product(1L)));
        when(categoryRepository.findById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.partialUpdate(1L, patch))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void partialUpdate_updatesCategory_whenCategoryIdPresent() {
        Product p = product(1L);
        ProductPatch patch = new ProductPatch();
        patch.setCategoryId(2L);
        Category newCat = new Category(2L, "NewCat", false);
        ProductResponse expected = new ProductResponse();

        when(productRepository.findById(1L)).thenReturn(Optional.of(p));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(newCat));
        when(productRepository.save(p)).thenReturn(p);
        when(productMapper.toResponse(p)).thenReturn(expected);

        assertThat(productService.partialUpdate(1L, patch)).isEqualTo(expected);
        assertThat(p.getCategory()).isEqualTo(newCat);
        verify(productValidator).validatePatch(patch, 1L);
        verify(productMapper).updateEntityFromPatch(p, patch);
    }

    @Test
    void partialUpdate_keepsCategory_whenCategoryIdNull() {
        Product p = product(1L);
        Category original = category();
        p.setCategory(original);
        ProductPatch patch = new ProductPatch();
        patch.setName("NewName");
        ProductResponse expected = new ProductResponse();

        when(productRepository.findById(1L)).thenReturn(Optional.of(p));
        when(productRepository.save(p)).thenReturn(p);
        when(productMapper.toResponse(p)).thenReturn(expected);

        assertThat(productService.partialUpdate(1L, patch)).isEqualTo(expected);
        assertThat(p.getCategory()).isEqualTo(original);
        verify(categoryRepository, never()).findById(anyLong());
    }
}
