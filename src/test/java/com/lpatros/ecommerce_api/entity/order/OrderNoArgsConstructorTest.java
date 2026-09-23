package com.lpatros.ecommerce_api.entity.order;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderNoArgsConstructorTest {

    @Test
    void noArgsConstructor_producesEmptyInstance() {
        Order order = new Order();
        assertThat(order.getId()).isNull();
        assertThat(order.getStatus()).isNull();
        assertThat(order.getTotalPrice()).isNull();
    }
}
