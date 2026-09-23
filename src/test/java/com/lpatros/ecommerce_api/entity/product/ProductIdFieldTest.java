package com.lpatros.ecommerce_api.entity.product;

import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductIdFieldTest {

    @Test
    void idIsNullBeforePersist() {
        Product product = new Product();
        assertThat(product.getId()).isNull();
        product.setId(10L);
        assertThat(product.getId()).isEqualTo(10L);
    }
}
