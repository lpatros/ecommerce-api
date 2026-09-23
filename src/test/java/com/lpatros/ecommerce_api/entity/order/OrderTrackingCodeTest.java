package com.lpatros.ecommerce_api.entity.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTrackingCodeTest {

    @Test
    void trackingCode_isNullableAndSettable() {
        Order order = new Order(1L, List.of(), BigDecimal.ZERO, OrderStatus.PENDING, null, null, null, false);
        assertThat(order.getTrackingCode()).isNull();
        order.setTrackingCode("BR123456");
        assertThat(order.getTrackingCode()).isEqualTo("BR123456");
    }
}
