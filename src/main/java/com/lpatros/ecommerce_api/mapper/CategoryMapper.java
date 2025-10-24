package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.dto.category.CategoryRequest;
import com.lpatros.ecommerce_api.dto.category.CategoryResponse;
import com.lpatros.ecommerce_api.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }

    public List<CategoryResponse> toResponseList(List<Category> categories) {
        return categories.stream()
                .map(this::toResponse)
                .toList();
    }

    public Category toEntity(CategoryRequest categoryRequest) {
        return new Category(
                null,
                categoryRequest.getName(),
                Boolean.FALSE
        );
    }
}
