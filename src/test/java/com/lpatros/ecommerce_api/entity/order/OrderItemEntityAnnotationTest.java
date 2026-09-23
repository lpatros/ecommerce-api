package com.lpatros.ecommerce_api.entity.order;

import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemEntityAnnotationTest {

    @Test
    void orderItemIsJpaEntity() {
        assertThat(OrderItem.class.getAnnotation(Entity.class)).isNotNull();
    }
}
