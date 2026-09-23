package com.lpatros.ecommerce_api.entity.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTotalPriceRequiredTest {

    @Test
    void totalPrice_acceptsZero_andPositiveValues() {
        Order order = new Order();
        order.setTotalPrice(BigDecimal.ZERO);
        assertThat(order.getTotalPrice()).isEqualByComparingTo(BigDecimal.ZERO);
        order.setTotalPrice(new BigDecimal("0.01"));
        assertThat(order.getTotalPrice()).isEqualByComparingTo(new BigDecimal("0.01"));
    }

    @Test
    void orderItems_mayBeNullBeforeInit() {
        Order order = new Order(1L, null, BigDecimal.ZERO, OrderStatus.PENDING, null, null, null, false);
        assertThat(order.getOrderItems()).isNull();
    }
}
