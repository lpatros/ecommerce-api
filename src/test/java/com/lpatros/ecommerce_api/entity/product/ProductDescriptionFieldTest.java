package com.lpatros.ecommerce_api.entity.product;

import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductDescriptionFieldTest {

    @Test
    void descriptionField_isNullable() {
        Product product = new Product();
        assertThat(product.getDescription()).isNull();
        product.setDescription("A detailed description");
        assertThat(product.getDescription()).isEqualTo("A detailed description");
    }
}
