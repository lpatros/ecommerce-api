package com.lpatros.ecommerce_api.entity.order;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderConstructorTest {

    @Test
    void allArgsConstructor_setsAllFields() {
        User user = new User();
        user.setId(5L);
        Order order = new Order(
                2L, List.of(), new BigDecimal("99.99"), OrderStatus.DELIVERED,
                "COD", user, null, false
        );
        assertThat(order.getId()).isEqualTo(2L);
        assertThat(order.getTotalPrice()).isEqualByComparingTo(new BigDecimal("99.99"));
        assertThat(order.getStatus()).isEqualTo(OrderStatus.DELIVERED);
        assertThat(order.getTrackingCode()).isEqualTo("COD");
        assertThat(order.getUser().getId()).isEqualTo(5L);
        assertThat(order.getDeleted()).isFalse();
    }
}
