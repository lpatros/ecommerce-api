package com.lpatros.ecommerce_api.entity.category;

import com.lpatros.ecommerce_api.entity.Category;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    @Test
    void gettersAndSetters_work() {
        Category category = new Category(1L, "Books", false);
        assertThat(category.getId()).isEqualTo(1L);
        assertThat(category.getName()).isEqualTo("Books");
        assertThat(category.getDeleted()).isFalse();

        category.setName("Fiction");
        category.setDeleted(true);
        assertThat(category.getName()).isEqualTo("Fiction");
        assertThat(category.getDeleted()).isTrue();
    }
}
