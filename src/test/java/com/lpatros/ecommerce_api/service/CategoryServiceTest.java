package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.category.CategoryFilter;
import com.lpatros.ecommerce_api.dto.category.CategoryRequest;
import com.lpatros.ecommerce_api.dto.category.CategoryResponse;
import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.mapper.CategoryMapper;
import com.lpatros.ecommerce_api.repository.CategoryRepository;
import com.lpatros.ecommerce_api.validator.CategoryValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

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
class CategoryServiceTest {

    @Mock private CategoryValidator categoryValidator;
    @Mock private CategoryRepository categoryRepository;
    @Mock private CategoryMapper categoryMapper;

    @InjectMocks private CategoryService categoryService;

    private Category category(Long id) {
        return new Category(id, "Electronics", false);
    }

    @Test
    void findById_returnsMappedResponse() {
        Category c = category(1L);
        CategoryResponse expected = new CategoryResponse();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(c));
        when(categoryMapper.toResponse(c)).thenReturn(expected);
        assertThat(categoryService.findById(1L)).isEqualTo(expected);
    }

    @Test
    void findById_throwsNotFound_whenMissing() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> categoryService.findById(99L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void create_validatesAndSaves() {
        CategoryRequest request = new CategoryRequest("Books");
        Category c = category(null);
        CategoryResponse response = new CategoryResponse();
        when(categoryMapper.toEntity(request)).thenReturn(c);
        when(categoryRepository.save(c)).thenReturn(c);
        when(categoryMapper.toResponse(c)).thenReturn(response);
        assertThat(categoryService.create(request)).isEqualTo(response);
        verify(categoryValidator).validateCreate(request);
    }

    @Test
    void update_throwsNotFound_whenMissing() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> categoryService.update(1L, new CategoryRequest("X")))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void update_preservesId() {
        Category existing = category(1L);
        CategoryRequest request = new CategoryRequest("NewName");
        Category mapped = category(null);
        CategoryResponse expected = new CategoryResponse();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryMapper.toEntity(request)).thenReturn(mapped);
        when(categoryRepository.save(mapped)).thenReturn(mapped);
        when(categoryMapper.toResponse(mapped)).thenReturn(expected);
        assertThat(categoryService.update(1L, request)).isEqualTo(expected);
        assertThat(mapped.getId()).isEqualTo(1L);
    }

    @Test
    void delete_callsRepository_whenExists() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category(1L)));
        categoryService.delete(1L);
        verify(categoryRepository).deleteById(1L);
    }

    @Test
    void delete_throwsNotFound_whenMissing() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> categoryService.delete(1L)).isInstanceOf(NotFoundException.class);
        verify(categoryRepository, never()).deleteById(anyLong());
    }

    @Test
    void findAll_returnsMappedPagination() {
        Category c = category(1L);
        CategoryResponse cr = new CategoryResponse();
        var page = new PageImpl<>(List.of(c), PageRequest.of(0, 10), 1);
        when(categoryRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(page);
        when(categoryMapper.toResponsePagination(page)).thenReturn(new Pagination<>(page.map(x -> cr)));
        Pagination<CategoryResponse> result = categoryService.findAll(new CategoryFilter(), PageRequest.of(0, 10));
        assertThat(result.getContent()).containsExactly(cr);
    }
}
