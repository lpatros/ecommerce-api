package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.category.CategoryRequest;
import com.lpatros.ecommerce_api.dto.category.CategoryResponse;
import com.lpatros.ecommerce_api.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryMapperTest {

    private final CategoryMapper categoryMapper = new CategoryMapper();

    @Test
    void toResponse_mapsFields() {
        Category category = new Category(1L, "Electronics", false);
        CategoryResponse response = categoryMapper.toResponse(category);
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Electronics");
    }

    @Test
    void toEntity_setsNameAndNotDeleted() {
        Category category = categoryMapper.toEntity(new CategoryRequest("Books"));
        assertThat(category.getId()).isNull();
        assertThat(category.getName()).isEqualTo("Books");
        assertThat(category.getDeleted()).isFalse();
    }

    @Test
    void toResponsePagination_mapsPage() {
        Page<Category> page = new PageImpl<>(List.of(new Category(1L, "A", false)));
        Pagination<CategoryResponse> pagination = categoryMapper.toResponsePagination(page);
        assertThat(pagination.getContent()).hasSize(1);
        assertThat(pagination.getTotalElements()).isEqualTo(1);
        assertThat(pagination.getPageNumber()).isZero();
    }

    @Test
    void toResponsePagination_emptyPage() {
        Page<Category> page = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);
        Pagination<CategoryResponse> pagination = categoryMapper.toResponsePagination(page);
        assertThat(pagination.getContent()).isEmpty();
    }
}
