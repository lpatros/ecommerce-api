package com.lpatros.ecommerce_api.entity.product;

import com.lpatros.ecommerce_api.entity.Product;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductSoftDeleteAnnotationTest {

    @Test
    void productHasSoftDeleteAnnotations() {
        SQLDelete sqlDelete = Product.class.getAnnotation(SQLDelete.class);
        SQLRestriction sqlRestriction = Product.class.getAnnotation(SQLRestriction.class);
        assertThat(sqlDelete).isNotNull();
        assertThat(sqlDelete.sql()).contains("UPDATE products SET deleted = TRUE");
        assertThat(sqlRestriction).isNotNull();
        assertThat(sqlRestriction.value()).isEqualTo("deleted = FALSE");
    }
}
