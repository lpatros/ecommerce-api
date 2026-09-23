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

class UserOrdersCascadeTest {

    @Test
    void multipleOrders_canBeAttached() {
        User user = new User(
                1L, "cpf", "name", "phone", "e@x.com",
                "pwd", LocalDate.of(2000, 1, 1), null, new ArrayList<>(),
                null, false, "USER"
        );
        for (long i = 1; i <= 3; i++) {
            user.getOrders().add(new Order(i, List.of(), BigDecimal.TEN, OrderStatus.PENDING,
                    null, user, null, false));
        }
        assertThat(user.getOrders()).hasSize(3);
        assertThat(user.getOrders()).extracting(Order::getId).containsExactly(1L, 2L, 3L);
    }
}
