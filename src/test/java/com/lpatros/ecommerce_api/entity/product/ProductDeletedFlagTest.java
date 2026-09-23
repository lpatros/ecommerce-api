package com.lpatros.ecommerce_api.entity.product;

import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductDeletedFlagTest {

    @Test
    void deletedFlag_defaultsToFalse_whenSetExplicitly() {
        Product product = new Product();
        product.setDeleted(false);
        assertThat(product.getDeleted()).isFalse();
        product.setDeleted(true);
        assertThat(product.getDeleted()).isTrue();
    }
}
