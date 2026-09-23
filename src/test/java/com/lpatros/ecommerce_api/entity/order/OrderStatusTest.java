package com.lpatros.ecommerce_api.entity.order;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderStatusTest {

    @Test
    void allExpectedConstantsExist() {
        assertThat(OrderStatus.values())
                .extracting(OrderStatus::name)
                .containsExactly("PENDING", "PROCESSING", "SHIPPED", "DELIVERED", "CANCELED", "RETURNED");
    }
}
