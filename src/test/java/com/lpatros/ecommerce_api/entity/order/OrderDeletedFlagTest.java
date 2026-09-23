package com.lpatros.ecommerce_api.entity.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderDeletedFlagTest {

    @Test
    void deletedFlag_controlsSoftDeleteState() {
        Order order = new Order(1L, List.of(), BigDecimal.ZERO, OrderStatus.PENDING,
                null, null, null, false);
        assertThat(order.getDeleted()).isFalse();
        order.setDeleted(true);
        assertThat(order.getDeleted()).isTrue();
    }
}
