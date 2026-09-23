package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import com.lpatros.ecommerce_api.entity.order.Order;
import com.lpatros.ecommerce_api.entity.order.OrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserOrdersRelationTest {

    @Test
    void userHoldsOrdersList() {
        User user = new User(
                1L, "cpf", "name", "phone", "e@x.com",
                "pwd", LocalDate.of(2000, 1, 1), null, new ArrayList<>(),
                null, false, "USER"
        );
        Order order = new Order(1L, List.of(), BigDecimal.TEN, OrderStatus.PENDING,
                null, user, null, false);
        user.getOrders().add(order);
        assertThat(user.getOrders()).hasSize(1);
        assertThat(user.getOrders().getFirst().getId()).isEqualTo(1L);
    }

    @Test
    void userOrders_canBeNull_beforeInit() {
        User user = new User(
                1L, "cpf", "name", "phone", "e@x.com",
                "pwd", LocalDate.of(2000, 1, 1), null, null,
                null, false, "USER"
        );
        assertThat(user.getOrders()).isNull();
    }
}
