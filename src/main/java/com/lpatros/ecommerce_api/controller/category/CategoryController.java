package com.lpatros.ecommerce_api.controller.category;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.category.CategoryFilter;
import com.lpatros.ecommerce_api.dto.category.CategoryRequest;
import com.lpatros.ecommerce_api.dto.category.CategoryResponse;
import com.lpatros.ecommerce_api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController implements Category {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public ResponseEntity<Pagination<CategoryResponse>> findAll(CategoryFilter categoryFilter, Pageable pageable) {
        return ResponseEntity.ok(categoryService.findAll(categoryFilter, pageable));
    }

    public ResponseEntity<CategoryResponse> findById(Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    public ResponseEntity<CategoryResponse> create(CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.create(categoryRequest));
    }

    public ResponseEntity<CategoryResponse> update(Long id, CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.update(id, categoryRequest));
    }

    public ResponseEntity<Void> delete(Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
