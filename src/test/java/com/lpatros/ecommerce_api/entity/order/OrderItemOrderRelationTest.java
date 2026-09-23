package com.lpatros.ecommerce_api.entity.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemOrderRelationTest {

    @Test
    void orderItemHoldsOrderReference() {
        Order order = new Order();
        order.setId(11L);
        OrderItem item = new OrderItem();
        item.setOrder(order);
        assertThat(item.getOrder().getId()).isEqualTo(11L);
        item.setOrder(null);
        assertThat(item.getOrder()).isNull();
    }

    @Test
    void unitPriceAndQuantity_independentOfEachOther() {
        OrderItem item = new OrderItem(null, 3, BigDecimal.valueOf(10), null, null, false);
        assertThat(item.getQuantity()).isEqualTo(3);
        assertThat(item.getUnitPrice()).isEqualByComparingTo(BigDecimal.TEN);
    }
}
