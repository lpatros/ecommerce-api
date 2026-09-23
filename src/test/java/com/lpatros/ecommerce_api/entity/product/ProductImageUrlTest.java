package com.lpatros.ecommerce_api.entity.product;

import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductImageUrlTest {

    @Test
    void imageUrl_isRequiredByConstructor_andSettable() {
        Product product = new Product(1L, "n", "d", 1, BigDecimal.ONE, "img.png", null, null, false, null);
        assertThat(product.getImageUrl()).isEqualTo("img.png");
        product.setImageUrl("other.png");
        assertThat(product.getImageUrl()).isEqualTo("other.png");
    }
}
