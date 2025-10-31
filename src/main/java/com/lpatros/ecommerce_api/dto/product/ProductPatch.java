package com.lpatros.ecommerce_api.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPatch {
    private String name;

    private String description;

    private Integer stock;

    private BigDecimal price;

    private String imageUrl;

    private Long categoryId;
}
