package com.lpatros.ecommerce_api.entity.category;

import com.lpatros.ecommerce_api.entity.Category;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryEntityAnnotationTest {

    @Test
    void categoryIsJpaEntity() {
        assertThat(Category.class.getAnnotation(Entity.class)).isNotNull();
    }
}
