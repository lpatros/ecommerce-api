package com.lpatros.ecommerce_api.entity.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderIdFieldTest {

    @Test
    void idIsNullBeforePersist() {
        Order order = new Order();
        assertThat(order.getId()).isNull();
        order.setId(100L);
        assertThat(order.getId()).isEqualTo(100L);
    }

    @Test
    void idSetViaConstructor() {
        Order order = new Order(42L, List.of(), BigDecimal.ZERO, OrderStatus.PENDING,
                null, null, null, false);
        assertThat(order.getId()).isEqualTo(42L);
    }
}
