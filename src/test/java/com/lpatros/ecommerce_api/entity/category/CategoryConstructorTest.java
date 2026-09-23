package com.lpatros.ecommerce_api.entity.category;

import com.lpatros.ecommerce_api.entity.Category;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryConstructorTest {

    @Test
    void allArgsConstructor_setsAllFields() {
        Category category = new Category(3L, "Sports", true);
        assertThat(category.getId()).isEqualTo(3L);
        assertThat(category.getName()).isEqualTo("Sports");
        assertThat(category.getDeleted()).isTrue();
    }

    @Test
    void noArgsConstructor_producesEmptyInstance() {
        Category category = new Category();
        assertThat(category.getId()).isNull();
        assertThat(category.getName()).isNull();
        assertThat(category.getDeleted()).isNull();
    }
}
