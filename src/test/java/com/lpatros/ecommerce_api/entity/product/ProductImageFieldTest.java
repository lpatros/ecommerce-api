package com.lpatros.ecommerce_api.entity.product;

import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductImageFieldTest {

    @Test
    void imageUrlAndDescription_areSettable() {
        Product product = new Product();
        product.setImageUrl("http://img/1.png");
        product.setDescription("Nice product");
        assertThat(product.getImageUrl()).isEqualTo("http://img/1.png");
        assertThat(product.getDescription()).isEqualTo("Nice product");
    }

    @Test
    void createdAtUpdatedAt_nullableBeforePersist() {
        Product product = new Product(1L, "n", "d", 1, BigDecimal.ONE, "img", null, null, false, null);
        assertThat(product.getCreatedAt()).isNull();
        assertThat(product.getUpdatedAt()).isNull();
    }
}
