package com.lpatros.ecommerce_api.validator;

import com.lpatros.ecommerce_api.dto.product.ProductRequest;
import com.lpatros.ecommerce_api.exception.NotNegativeException;
import com.lpatros.ecommerce_api.exception.NotUniqueException;
import com.lpatros.ecommerce_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductValidator {

    private final ProductRepository productRepository;

    @Autowired
    public ProductValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void validateCreate(ProductRequest productRequest) {
        validateNameUnique(productRequest.getName(), null);
    }

    public void validateUpdate(ProductRequest productRequest, Long productId) {
        validateNameUnique(productRequest.getName(), productId);
        validateStockNonNegative(productRequest.getStock());
        validatePriceNonNegative(productRequest.getPrice());
    }

    private void validateNameUnique(String name, Long idToIgnore) {
        boolean exists;
        if (idToIgnore == null) {
            exists = productRepository.existsByName(name);
        } else {
            exists = productRepository.existsByNameAndIdNot(name, idToIgnore);
        }
        if (exists) {
            throw new NotUniqueException("Product", "name");
        }
    }

    private void validateStockNonNegative(int stock) {
        if (stock < 0) {
            throw new NotNegativeException("Product", "stock");
        }
    }

    private void validatePriceNonNegative(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotNegativeException("Product", "price");
        }
    }
}
