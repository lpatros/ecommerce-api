package com.lpatros.ecommerce_api.entity.product;

import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductCategoryRelationTest {

    @Test
    void productHoldsCategoryReference() {
        Category category = new Category(5L, "Gaming", false);
        Product product = new Product(1L, "Console", "desc", 3, BigDecimal.TEN,
                "img", null, null, false, category);
        assertThat(product.getCategory()).isSameAs(category);
        assertThat(product.getCategory().getId()).isEqualTo(5L);

        Category other = new Category(6L, "PC", false);
        product.setCategory(other);
        assertThat(product.getCategory().getName()).isEqualTo("PC");
    }
}
