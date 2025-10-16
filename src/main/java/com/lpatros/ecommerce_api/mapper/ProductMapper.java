package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.dto.category.CategoryRequest;
import com.lpatros.ecommerce_api.dto.product.ProductRequest;
import com.lpatros.ecommerce_api.dto.product.ProductResponse;
import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getStock(),
            product.getPrice(),
            product.getImageUrl(),
            new CategoryMapper().toResponse(product.getCategory())
        );
    }

    public Product toEntity(ProductRequest request, Category category) {
        return new Product(
            null,
            request.getName(),
            request.getDescription(),
            request.getStock(),
            request.getPrice(),
            request.getImageUrl(),
            category
        );
    }
}
