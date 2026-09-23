package com.lpatros.ecommerce_api.validator;

import com.lpatros.ecommerce_api.dto.product.ProductPatch;
import com.lpatros.ecommerce_api.dto.product.ProductRequest;
import com.lpatros.ecommerce_api.exception.NotNegativeException;
import com.lpatros.ecommerce_api.exception.NotUniqueException;
import com.lpatros.ecommerce_api.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductValidatorTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductValidator productValidator;

    private ProductRequest request(String name, Integer stock, BigDecimal price) {
        ProductRequest r = new ProductRequest();
        r.setName(name);
        r.setDescription("desc");
        r.setStock(stock);
        r.setPrice(price);
        r.setImageUrl("img.png");
        r.setCategoryId(1L);
        return r;
    }

    @Test
    void validateCreate_throws_whenNameExists() {
        when(productRepository.existsByName("Widget")).thenReturn(true);
        assertThatThrownBy(() -> productValidator.validateCreate(request("Widget", 5, BigDecimal.TEN)))
                .isInstanceOf(NotUniqueException.class);
    }

    @Test
    void validateCreate_passes_whenNameUnique() {
        when(productRepository.existsByName("Widget")).thenReturn(false);
        assertThatCode(() -> productValidator.validateCreate(request("Widget", 5, BigDecimal.TEN)))
                .doesNotThrowAnyException();
    }

    @Test
    void validateUpdate_throws_whenNameExistsForAnotherId() {
        when(productRepository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(true);
        assertThatThrownBy(() -> productValidator.validateUpdate(request("Widget", 5, BigDecimal.TEN), 1L))
                .isInstanceOf(NotUniqueException.class);
    }

    @Test
    void validateUpdate_throws_whenStockNegative() {
        when(productRepository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(false);
        assertThatThrownBy(() -> productValidator.validateUpdate(request("Widget", -1, BigDecimal.TEN), 1L))
                .isInstanceOf(NotNegativeException.class)
                .hasMessageContaining("stock");
    }

    @Test
    void validateUpdate_throws_whenPriceNegative() {
        when(productRepository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(false);
        assertThatThrownBy(() -> productValidator.validateUpdate(request("Widget", 5, BigDecimal.valueOf(-1)), 1L))
                .isInstanceOf(NotNegativeException.class)
                .hasMessageContaining("price");
    }

    @Test
    void validateUpdate_passes_whenValid() {
        when(productRepository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(false);
        assertThatCode(() -> productValidator.validateUpdate(request("Widget", 5, BigDecimal.TEN), 1L))
                .doesNotThrowAnyException();
    }

    @Test
    void validatePatch_skipsNullFields() {
        ProductPatch patch = new ProductPatch();
        patch.setStock(-5);
        assertThatThrownBy(() -> productValidator.validatePatch(patch, 1L))
                .isInstanceOf(NotNegativeException.class);
    }

    @Test
    void validatePatch_allNull_passes() {
        assertThatCode(() -> productValidator.validatePatch(new ProductPatch(), 1L))
                .doesNotThrowAnyException();
    }
}
