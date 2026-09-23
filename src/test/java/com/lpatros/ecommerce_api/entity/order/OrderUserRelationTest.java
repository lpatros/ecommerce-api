package com.lpatros.ecommerce_api.entity.order;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderUserRelationTest {

    @Test
    void orderHoldsUserReference() {
        User user = new User(
                9L, "cpf", "name", "phone", "e@x.com",
                "pwd", LocalDate.of(2000, 1, 1), null, List.of(),
                null, false, "USER"
        );
        Order order = new Order(1L, List.of(), BigDecimal.ZERO, OrderStatus.PENDING, null, user, null, false);
        assertThat(order.getUser()).isSameAs(user);
        assertThat(order.getUser().getId()).isEqualTo(9L);
    }
}
