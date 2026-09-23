package com.lpatros.ecommerce_api.entity.order;

import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTableAnnotationTest {

    @Test
    void orderMapsToOrdersTable() {
        Table table = Order.class.getAnnotation(Table.class);
        assertThat(table).isNotNull();
        assertThat(table.name()).isEqualTo("orders");
    }
}
