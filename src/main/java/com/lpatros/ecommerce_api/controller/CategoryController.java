package com.lpatros.ecommerce_api.controller;

import com.lpatros.ecommerce_api.dto.CategoryRequest;
import com.lpatros.ecommerce_api.dto.CategoryResponse;
import com.lpatros.ecommerce_api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findByStatus(@RequestParam(required = false, name = "status", defaultValue = "true") String status) {

        boolean statusBoolean = true;
        if (status != null && !status.isEmpty() && (status.equalsIgnoreCase("true") || status.equalsIgnoreCase("false"))) {
            statusBoolean = Boolean.parseBoolean(status);
        }

        return ResponseEntity.ok(categoryService.findByStatus(statusBoolean));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.create(categoryRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.update(id, categoryRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
