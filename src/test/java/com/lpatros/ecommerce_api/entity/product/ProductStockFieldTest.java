package com.lpatros.ecommerce_api.entity.product;

import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductStockFieldTest {

    @Test
    void stockField_isIntegerNotNull_semantics() {
        Product product = new Product();
        product.setStock(0);
        assertThat(product.getStock()).isEqualTo(0);

        product.setStock(null);
        assertThat(product.getStock()).isNull();
    }

    @Test
    void priceField_holdsBigDecimal() {
        Product product = new Product();
        product.setPrice(new BigDecimal("19.99"));
        assertThat(product.getPrice()).isEqualByComparingTo(new BigDecimal("19.99"));
    }
}
