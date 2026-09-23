package com.lpatros.ecommerce_api.entity.category;

import com.lpatros.ecommerce_api.entity.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryDeletedFlagTest {

    @Test
    void deletedFlag_defaultsFromConstructor() {
        Assertions.assertThat(new Category(1L, "X", false).getDeleted()).isFalse();
        assertThat(new Category(1L, "X", true).getDeleted()).isTrue();
    }
}
