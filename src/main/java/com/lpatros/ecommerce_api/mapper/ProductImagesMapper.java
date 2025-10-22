package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.dto.productImage.ProductImageRequest;
import com.lpatros.ecommerce_api.dto.productImage.ProductImageResponse;
import com.lpatros.ecommerce_api.entity.ProductImage;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ProductImagesMapper {

    public ProductImageResponse toResponse(ProductImage productImage) {
        return new ProductImageResponse(
                productImage.getUrl(),
                productImage.getIsCover()
        );
    }

    public ProductImage toEntity(ProductImageRequest productImageRequest) {
        return new ProductImage(
                null,
                null,
                productImageRequest.getUrl(),
                (productImageRequest.getIsCover() != null) ? productImageRequest.getIsCover() : Boolean.FALSE
        );
    }

    public List<ProductImage> toEntityList(List<ProductImageRequest> productImageRequests) {
        return productImageRequests.stream()
                .map(this::toEntity)
                .toList();
    }
}
