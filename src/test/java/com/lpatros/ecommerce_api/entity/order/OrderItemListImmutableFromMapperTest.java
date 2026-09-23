package com.lpatros.ecommerce_api.entity.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemListImmutableFromMapperTest {

    @Test
    void orderItems_canBeReplaced() {
        Order order = new Order(1L, List.of(), BigDecimal.ZERO, OrderStatus.PENDING,
                null, null, null, false);
        OrderItem item = new OrderItem(1L, 1, BigDecimal.ONE, null, order, false);
        order.setOrderItems(List.of(item));
        assertThat(order.getOrderItems()).hasSize(1);
        assertThat(order.getOrderItems().getFirst().getId()).isEqualTo(1L);
    }
}
