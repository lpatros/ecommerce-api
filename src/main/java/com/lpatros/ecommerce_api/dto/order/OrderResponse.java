package com.lpatros.ecommerce_api.dto.order;

import com.lpatros.ecommerce_api.dto.order.orderItem.OrderItemResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long id;

    private List<OrderItemResponse> orderItems;

    private BigDecimal totalPrice;

    private String status;

    private String trackingCode;

    private Long userId;

    private LocalDateTime createdAt;
}
