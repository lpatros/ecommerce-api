package com.lpatros.ecommerce_api.entity.order;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderStatusEnumValuesTest {

    @Test
    void sixConstants_expectedNames() {
        assertThat(OrderStatus.values()).hasSize(6);
        assertThat(OrderStatus.valueOf("PROCESSING")).isEqualTo(OrderStatus.PROCESSING);
        assertThat(OrderStatus.valueOf("SHIPPED")).isEqualTo(OrderStatus.SHIPPED);
    }
}
