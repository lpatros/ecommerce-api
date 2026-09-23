package com.lpatros.ecommerce_api.entity.product;

import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductStockIntegerTest {

    @Test
    void stockAcceptsZeroAndLargeValues() {
        Product product = new Product();
        product.setStock(0);
        assertThat(product.getStock()).isZero();
        product.setStock(Integer.MAX_VALUE);
        assertThat(product.getStock()).isEqualTo(Integer.MAX_VALUE);
    }
}
