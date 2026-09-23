package com.lpatros.ecommerce_api.entity.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderCreatedAtFieldTest {

    @Test
    void createdAt_isNullableBeforePersist() {
        Order order = new Order(1L, List.of(), BigDecimal.ZERO, OrderStatus.PENDING,
                null, null, null, false);
        assertThat(order.getCreatedAt()).isNull();
        LocalDateTime now = LocalDateTime.now();
        order.setCreatedAt(now);
        assertThat(order.getCreatedAt()).isEqualTo(now);
    }
}
