package com.lpatros.ecommerce_api.entity.order;

import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemTest {

    @Test
    void gettersAndSetters_work() {
        Product product = new Product();
        product.setId(1L);

        OrderItem item = new OrderItem(1L, 3, BigDecimal.TEN, product, null, false);
        assertThat(item.getId()).isEqualTo(1L);
        assertThat(item.getQuantity()).isEqualTo(3);
        assertThat(item.getUnitPrice()).isEqualByComparingTo(BigDecimal.TEN);
        assertThat(item.getProduct().getId()).isEqualTo(1L);
        assertThat(item.getDeleted()).isFalse();

        item.setQuantity(5);
        assertThat(item.getQuantity()).isEqualTo(5);
    }
}
