package com.lpatros.ecommerce_api.entity.product;

import com.lpatros.ecommerce_api.entity.Product;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductEntityAnnotationTest {

    @Test
    void productIsJpaEntity() {
        assertThat(Product.class.getAnnotation(Entity.class)).isNotNull();
    }
}
