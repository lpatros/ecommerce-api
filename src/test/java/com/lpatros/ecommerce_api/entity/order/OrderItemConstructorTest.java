package com.lpatros.ecommerce_api.entity.order;

import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemConstructorTest {

    @Test
    void allArgsConstructor_setsAllFields() {
        Product product = new Product();
        product.setId(8L);
        Order order = new Order();
        order.setId(3L);

        OrderItem item = new OrderItem(4L, 6, BigDecimal.valueOf(12.34), product, order, true);

        assertThat(item.getId()).isEqualTo(4L);
        assertThat(item.getQuantity()).isEqualTo(6);
        assertThat(item.getUnitPrice()).isEqualByComparingTo(BigDecimal.valueOf(12.34));
        assertThat(item.getProduct().getId()).isEqualTo(8L);
        assertThat(item.getOrder().getId()).isEqualTo(3L);
        assertThat(item.getDeleted()).isTrue();
    }
}
