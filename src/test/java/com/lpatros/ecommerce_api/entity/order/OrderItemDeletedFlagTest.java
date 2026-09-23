package com.lpatros.ecommerce_api.entity.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemDeletedFlagTest {

    @Test
    void deletedFlag_defaultsWhenConstructed() {
        OrderItem item = new OrderItem(1L, 1, BigDecimal.ONE, null, null, false);
        assertThat(item.getDeleted()).isFalse();
        item.setDeleted(true);
        assertThat(item.getDeleted()).isTrue();
    }
}
