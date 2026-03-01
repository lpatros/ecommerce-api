package com.lpatros.ecommerce_api.controller.product;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.product.ProductFilter;
import com.lpatros.ecommerce_api.dto.product.ProductPatch;
import com.lpatros.ecommerce_api.dto.product.ProductRequest;
import com.lpatros.ecommerce_api.dto.product.ProductResponse;
import com.lpatros.ecommerce_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController implements Product {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public ResponseEntity<Pagination<ProductResponse>> findAll(ProductFilter productFilter, Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(productFilter, pageable));
    }

    public ResponseEntity<ProductResponse> findById(Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    public ResponseEntity<ProductResponse> create(ProductRequest productRequest) {
        return ResponseEntity.ok(productService.create(productRequest));
    }

    public ResponseEntity<ProductResponse> update(Long id, ProductRequest productRequest) {
        return ResponseEntity.ok(productService.update(id, productRequest));
    }

    public ResponseEntity<ProductResponse> partialUpdate(Long id, ProductPatch productPatch) {
        return ResponseEntity.ok(productService.partialUpdate(id, productPatch));
    }

    public ResponseEntity<Void> delete(Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
