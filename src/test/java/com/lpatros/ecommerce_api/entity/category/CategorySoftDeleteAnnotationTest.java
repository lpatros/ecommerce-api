package com.lpatros.ecommerce_api.entity.category;

import com.lpatros.ecommerce_api.entity.Category;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategorySoftDeleteAnnotationTest {

    @Test
    void categoryHasSoftDeleteAnnotations() {
        SQLDelete sqlDelete = Category.class.getAnnotation(SQLDelete.class);
        SQLRestriction sqlRestriction = Category.class.getAnnotation(SQLRestriction.class);
        assertThat(sqlDelete).isNotNull();
        assertThat(sqlDelete.sql()).contains("UPDATE categories SET deleted = true");
        assertThat(sqlRestriction).isNotNull();
        assertThat(sqlRestriction.value()).isEqualTo("deleted = false");
    }
}
