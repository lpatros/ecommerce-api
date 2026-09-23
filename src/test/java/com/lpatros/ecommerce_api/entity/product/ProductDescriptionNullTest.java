package com.lpatros.ecommerce_api.entity.product;

import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductDescriptionNullTest {

    @Test
    void descriptionMayBeNull() {
        Product product = new Product(1L, "n", null, 1, BigDecimal.ONE, "img", null, null, false, null);
        assertThat(product.getDescription()).isNull();
    }
}
