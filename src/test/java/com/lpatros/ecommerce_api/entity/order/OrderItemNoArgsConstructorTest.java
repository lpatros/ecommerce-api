package com.lpatros.ecommerce_api.entity.order;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemNoArgsConstructorTest {

    @Test
    void noArgsConstructor_producesEmptyInstance() {
        OrderItem item = new OrderItem();
        assertThat(item.getId()).isNull();
        assertThat(item.getQuantity()).isNull();
        assertThat(item.getUnitPrice()).isNull();
        assertThat(item.getDeleted()).isNull();
    }
}
