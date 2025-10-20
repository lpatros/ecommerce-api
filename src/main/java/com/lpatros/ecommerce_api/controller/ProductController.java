package com.lpatros.ecommerce_api.controller;

import com.lpatros.ecommerce_api.dto.product.ProductFilter;
import com.lpatros.ecommerce_api.dto.product.ProductRequest;
import com.lpatros.ecommerce_api.dto.product.ProductResponse;
import com.lpatros.ecommerce_api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get all Products with filters", method = "GET")
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> findAll(@ModelAttribute ProductFilter productFilter, Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(productFilter, pageable));
    }

    @Operation(summary = "Get a Product by Id", method = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @Operation(summary = "Create a new Product", method = "POST")
    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productService.create(productRequest));
    }

    @Operation(summary = "Update a Product by Id", method = "PUT")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productService.update(id, productRequest));
    }

    @Operation(summary = "Delete a Product by Id", method = "DELETE")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
