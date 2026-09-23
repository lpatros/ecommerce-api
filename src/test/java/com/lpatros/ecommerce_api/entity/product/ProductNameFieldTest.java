package com.lpatros.ecommerce_api.entity.product;

import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductNameFieldTest {

    @Test
    void nameField_storesValue() {
        Product product = new Product();
        product.setName("Keyboard");
        assertThat(product.getName()).isEqualTo("Keyboard");
        product.setName("Mechanical Keyboard");
        assertThat(product.getName()).isEqualTo("Mechanical Keyboard");
    }
}
