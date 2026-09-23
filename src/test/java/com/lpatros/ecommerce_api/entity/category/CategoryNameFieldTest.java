package com.lpatros.ecommerce_api.entity.category;

import com.lpatros.ecommerce_api.entity.Category;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryNameFieldTest {

    @Test
    void nameField_storesValue() {
        Category category = new Category();
        category.setName("Toys");
        assertThat(category.getName()).isEqualTo("Toys");
        category.setName("Games");
        assertThat(category.getName()).isEqualTo("Games");
    }
}
