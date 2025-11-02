package com.lpatros.ecommerce_api.dto.order.orderItem;

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
public class OrderItemRequest {

    @NotNull(message = "The quantity of the order item is required")
    private Integer quantity;

    @NotNull(message = "The unit price of the order item is required")
    private BigDecimal unitPrice;

    @NotNull(message = "The product ID of the order item is required")
    private Long productId;
}
