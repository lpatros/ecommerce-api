package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.product.ProductRequest;
import com.lpatros.ecommerce_api.dto.product.ProductResponse;
import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ProductMapper {

    private final CategoryMapper categoryMapper;

    @Autowired
    public ProductMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getStock(),
            product.getPrice(),
            product.getImageUrl(),
            product.getCreatedAt(),
            product.getUpdatedAt(),
            categoryMapper.toResponse(product.getCategory())
        );
    }

    public Pagination<ProductResponse> toResponsePagination(Page<Product> products) {
        return Pagination.toPagination(products.map(this::toResponse));
    }

    public Product toEntity(ProductRequest request, Category category) {
        return new Product(
            null,
            request.getName(),
            request.getDescription(),
            request.getStock(),
            request.getPrice(),
            request.getImageUrl(),
            null,
            null,
            Boolean.FALSE,
            category
        );
    }
}
