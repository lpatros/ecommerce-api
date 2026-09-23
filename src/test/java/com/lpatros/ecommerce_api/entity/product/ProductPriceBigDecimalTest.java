package com.lpatros.ecommerce_api.entity.product;

import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductPriceBigDecimalTest {

    @Test
    void price_preservesScale() {
        Product product = new Product();
        product.setPrice(new BigDecimal("10.00"));
        assertThat(product.getPrice().scale()).isEqualTo(2);
        assertThat(product.getPrice()).isEqualByComparingTo(new BigDecimal("10"));
    }
}
