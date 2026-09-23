package com.lpatros.ecommerce_api.validator;

import com.lpatros.ecommerce_api.dto.category.CategoryRequest;
import com.lpatros.ecommerce_api.exception.NotUniqueException;
import com.lpatros.ecommerce_api.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryValidatorTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryValidator categoryValidator;

    @Test
    void validateCreate_throws_whenNameExists() {
        when(categoryRepository.existsByName("Electronics")).thenReturn(true);
        assertThatThrownBy(() -> categoryValidator.validateCreate(new CategoryRequest("Electronics")))
                .isInstanceOf(NotUniqueException.class);
    }

    @Test
    void validateCreate_passes_whenNameUnique() {
        when(categoryRepository.existsByName("Electronics")).thenReturn(false);
        assertThatCode(() -> categoryValidator.validateCreate(new CategoryRequest("Electronics")))
                .doesNotThrowAnyException();
    }

    @Test
    void validateUpdate_throws_whenNameExistsForAnotherId() {
        when(categoryRepository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(true);
        assertThatThrownBy(() -> categoryValidator.validateUpdate(new CategoryRequest("Books"), 1L))
                .isInstanceOf(NotUniqueException.class);
    }

    @Test
    void validateUpdate_passes_whenNameUniqueIgnoringSelf() {
        when(categoryRepository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(false);
        assertThatCode(() -> categoryValidator.validateUpdate(new CategoryRequest("Books"), 1L))
                .doesNotThrowAnyException();
    }
}
