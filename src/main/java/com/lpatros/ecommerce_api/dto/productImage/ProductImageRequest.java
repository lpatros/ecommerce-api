package com.lpatros.ecommerce_api.dto.productImage;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageRequest {

    @NotBlank(message = "The URL of the product image is required")
    private String url;

    private Boolean isCover;
}
