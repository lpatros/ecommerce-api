package com.lpatros.ecommerce_api.entity.order;

import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemProductRelationTest {

    @Test
    void orderItemHoldsProductReference() {
        Product product = new Product();
        product.setId(42L);
        product.setPrice(BigDecimal.valueOf(15.00));

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(2);
        item.setUnitPrice(product.getPrice());

        assertThat(item.getProduct().getId()).isEqualTo(42L);
        assertThat(item.getUnitPrice()).isEqualByComparingTo(BigDecimal.valueOf(15.00));
    }
}
