package com.lpatros.ecommerce_api.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilter {
    private String name;
    private Long minStock;
    private Long maxStock;
    private Double minPrice;
    private Double maxPrice;
    private Long categoryId;
}
