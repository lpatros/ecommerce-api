package com.lpatros.ecommerce_api.entity.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderStatusTransitionTest {

    @Test
    void statusCanTransitionAcrossAllValues() {
        Order order = new Order(1L, List.of(), BigDecimal.ZERO, OrderStatus.PENDING,
                null, null, null, false);
        for (OrderStatus status : OrderStatus.values()) {
            order.setStatus(status);
            assertThat(order.getStatus()).isEqualTo(status);
        }
    }
}
