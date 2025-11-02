package com.lpatros.ecommerce_api.dto.order.orderItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {

    private Long id;

    private Integer quantity;

    private BigDecimal unitPrice;

    private Long productId;
}
