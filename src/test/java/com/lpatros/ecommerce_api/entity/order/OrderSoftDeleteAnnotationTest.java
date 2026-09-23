package com.lpatros.ecommerce_api.entity.order;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderSoftDeleteAnnotationTest {

    @Test
    void orderHasSoftDeleteAnnotations() {
        SQLDelete sqlDelete = Order.class.getAnnotation(SQLDelete.class);
        SQLRestriction sqlRestriction = Order.class.getAnnotation(SQLRestriction.class);
        assertThat(sqlDelete).isNotNull();
        assertThat(sqlDelete.sql()).contains("UPDATE orders SET deleted = true");
        assertThat(sqlRestriction).isNotNull();
        assertThat(sqlRestriction.value()).isEqualTo("deleted = false");
    }
}
