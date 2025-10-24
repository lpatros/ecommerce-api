package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.category.CategoryRequest;
import com.lpatros.ecommerce_api.dto.category.CategoryResponse;
import com.lpatros.ecommerce_api.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }

    public Pagination<CategoryResponse> toResponsePagination(Page<Category> categories) {
        return Pagination.toPagination(categories.map(this::toResponse));
    }

    public Category toEntity(CategoryRequest categoryRequest) {
        return new Category(
                null,
                categoryRequest.getName(),
                Boolean.FALSE
        );
    }
}
