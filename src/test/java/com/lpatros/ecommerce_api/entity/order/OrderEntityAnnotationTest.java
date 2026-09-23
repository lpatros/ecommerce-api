package com.lpatros.ecommerce_api.entity.order;

import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderEntityAnnotationTest {

    @Test
    void orderIsJpaEntity() {
        assertThat(Order.class.getAnnotation(Entity.class)).isNotNull();
    }
}
