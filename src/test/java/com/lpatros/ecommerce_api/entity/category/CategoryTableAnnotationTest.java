package com.lpatros.ecommerce_api.entity.category;

import com.lpatros.ecommerce_api.entity.Category;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTableAnnotationTest {

    @Test
    void categoryMapsToCategoriesTable() {
        Table table = Category.class.getAnnotation(Table.class);
        assertThat(table).isNotNull();
        assertThat(table.name()).isEqualTo("categories");
    }
}
