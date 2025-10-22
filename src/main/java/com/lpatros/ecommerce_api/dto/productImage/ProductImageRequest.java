package com.lpatros.ecommerce_api.dto.productImage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageRequest {
    private String url;
    private Boolean isCover;
}
