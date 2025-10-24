package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.dto.productImage.ProductImageResponse;
import com.lpatros.ecommerce_api.dto.product.ProductRequest;
import com.lpatros.ecommerce_api.dto.product.ProductResponse;
import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.entity.ProductImage;
import com.lpatros.ecommerce_api.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ProductMapper {

    private final CategoryMapper categoryMapper;
    private final ProductImagesMapper productImagesMapper;

    @Autowired
    public ProductMapper(CategoryMapper categoryMapper, ProductImagesMapper productImagesMapper) {
        this.productImagesMapper = productImagesMapper;
        this.categoryMapper = categoryMapper;
    }

    public ProductResponse toResponse(Product product) {

        List<ProductImageResponse> productImageResponsesList = 
            (product.getProductImages() != null && !product.getProductImages().isEmpty()) 
                ? product.getProductImages()
                    .stream()
                    .map(productImagesMapper::toResponse)
                    .toList()
                : List.of();

        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getStock(),
            product.getPrice(),
            productImageResponsesList,
            product.getCreatedAt(),
            product.getUpdatedAt(),
            categoryMapper.toResponse(product.getCategory())
        );
    }

    public Page<ProductResponse> toResponsePage(Page<Product> products) {
        return products.map(this::toResponse);
    }

    public Product toEntity(ProductRequest request, Category category) {
        
        Product product = new Product(
            null,
            request.getName(),
            request.getDescription(),
            request.getStock(),
            request.getPrice(),
            null,
            null,
            null,
            Boolean.FALSE,
            category
        );
        
        List<ProductImage> productImages = productImagesMapper.toEntityList(request.getProductImages());
        
        productImages.forEach(image -> image.setProduct(product));
        product.setProductImages(productImages);
        
        return product;
    }
}
