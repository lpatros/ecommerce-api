package com.lpatros.ecommerce_api.entity.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTotalPriceFieldTest {

    @Test
    void totalPrice_storesComputedValue() {
        Order order = new Order();
        order.setTotalPrice(new BigDecimal("123.45"));
        assertThat(order.getTotalPrice()).isEqualByComparingTo(new BigDecimal("123.45"));
    }

    @Test
    void orderItems_listIsMutable() {
        Order order = new Order();
        order.setOrderItems(new java.util.ArrayList<>(List.of()));
        order.getOrderItems().add(new OrderItem());
        assertThat(order.getOrderItems()).hasSize(1);
    }
}
