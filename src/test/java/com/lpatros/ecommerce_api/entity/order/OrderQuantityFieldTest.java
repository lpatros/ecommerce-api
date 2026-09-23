package com.lpatros.ecommerce_api.entity.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderQuantityFieldTest {

    @Test
    void quantityField_storesInteger() {
        OrderItem item = new OrderItem();
        item.setQuantity(7);
        assertThat(item.getQuantity()).isEqualTo(7);
        item.setQuantity(null);
        assertThat(item.getQuantity()).isNull();
    }

    @Test
    void unitPriceField_storesBigDecimal() {
        OrderItem item = new OrderItem();
        item.setUnitPrice(new BigDecimal("19.90"));
        assertThat(item.getUnitPrice()).isEqualByComparingTo(new BigDecimal("19.90"));
    }
}
