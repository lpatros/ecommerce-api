package com.lpatros.ecommerce_api.dto.order;

import com.lpatros.ecommerce_api.dto.order.orderItem.OrderItemRequest;
import com.lpatros.ecommerce_api.entity.order.OrderItem;
import com.lpatros.ecommerce_api.entity.order.OrderStatus;
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
public class OrderRequest {

    @NotNull(message = "The total price of the order is required")
    private BigDecimal totalPrice;

    @NotNull(message = "The order items are required")
    private List<OrderItemRequest> orderItems;

    @NotNull(message = "The status of the order is required")
    private OrderStatus status;

    private String trackingCode;

    @NotNull(message = "The user ID associated with the order is required")
    private Long userId;


}
