package com.lpatros.ecommerce_api.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "The name of the product is required")
    private String name;

    private String description;

    @NotNull(message = "The stock of the product is required")
    private Integer stock;

    @NotNull(message = "The price of the product is required")
    private BigDecimal price;

    @NotNull(message = "The image URL of the product is required")
    private String imageUrl;

    @NotNull(message = "The category ID of the product is required")
    private Long categoryId;
}
