package com.lpatros.ecommerce_api.dto.order;

import com.lpatros.ecommerce_api.entity.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderFilter {
    private OrderStatus status;
    private String trackingCode;
    private Long userId;
}
