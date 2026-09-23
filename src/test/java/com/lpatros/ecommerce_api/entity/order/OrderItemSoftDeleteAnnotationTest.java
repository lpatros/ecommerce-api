package com.lpatros.ecommerce_api.entity.order;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemSoftDeleteAnnotationTest {

    @Test
    void orderItemHasSoftDeleteAnnotations() {
        SQLDelete sqlDelete = OrderItem.class.getAnnotation(SQLDelete.class);
        SQLRestriction sqlRestriction = OrderItem.class.getAnnotation(SQLRestriction.class);
        assertThat(sqlDelete).isNotNull();
        assertThat(sqlDelete.sql()).contains("UPDATE order_items SET deleted = true");
        assertThat(sqlRestriction).isNotNull();
        assertThat(sqlRestriction.value()).isEqualTo("deleted = false");
    }
}
