package com.lpatros.ecommerce_api.entity.product;

import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductDeletedNullableTest {

    @Test
    void deletedMayBeNull_beforeFlush() {
        Product product = new Product();
        assertThat(product.getDeleted()).isNull();
    }
}
