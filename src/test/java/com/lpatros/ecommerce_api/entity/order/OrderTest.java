package com.lpatros.ecommerce_api.entity.order;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @Test
    void gettersAndSetters_work() {
        Order order = new Order(1L, List.of(), BigDecimal.TEN, OrderStatus.PENDING,
                "TRK", null, null, false);
        assertThat(order.getId()).isEqualTo(1L);
        assertThat(order.getTotalPrice()).isEqualByComparingTo(BigDecimal.TEN);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(order.getTrackingCode()).isEqualTo("TRK");
        assertThat(order.getDeleted()).isFalse();

        order.setStatus(OrderStatus.SHIPPED);
        order.setTotalPrice(BigDecimal.ONE);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.SHIPPED);
        assertThat(order.getTotalPrice()).isEqualByComparingTo(BigDecimal.ONE);
    }
}
