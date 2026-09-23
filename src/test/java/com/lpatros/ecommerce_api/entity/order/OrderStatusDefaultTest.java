package com.lpatros.ecommerce_api.entity.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderStatusDefaultTest {

    @Test
    void statusIsRequired_storedWhenSet() {
        Order order = new Order(1L, List.of(), BigDecimal.ZERO, null, null, null, null, false);
        assertThat(order.getStatus()).isNull();
        order.setStatus(OrderStatus.PENDING);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
    }

    @Test
    void statusEnum_roundTripsFromString() {
        assertThat(OrderStatus.valueOf("CANCELED")).isEqualTo(OrderStatus.CANCELED);
        assertThat(OrderStatus.valueOf("RETURNED")).isEqualTo(OrderStatus.RETURNED);
    }
}
