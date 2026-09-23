package com.lpatros.ecommerce_api.entity.product;

import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductNoArgsConstructorTest {

    @Test
    void noArgsConstructor_producesEmptyInstance() {
        Product product = new Product();
        assertThat(product.getId()).isNull();
        assertThat(product.getName()).isNull();
        assertThat(product.getStock()).isNull();
        assertThat(product.getPrice()).isNull();
    }
}
