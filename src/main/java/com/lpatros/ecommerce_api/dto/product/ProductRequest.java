package com.lpatros.ecommerce_api.dto.product;

import com.lpatros.ecommerce_api.dto.productImage.ProductImageRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "The name of the product is required")
    private String name;

    private String description;

    @NotNull(message = "The stock of the product is required")
    private Long stock;

    @NotNull(message = "The price of the product is required")
    private BigDecimal price;

    @NotNull(message = "The product must have at least one image")
    private List<ProductImageRequest> productImages;

    @NotNull(message = "The category ID of the product is required")
    private Long categoryId;
}
