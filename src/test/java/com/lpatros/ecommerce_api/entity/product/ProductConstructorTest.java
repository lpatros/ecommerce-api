package com.lpatros.ecommerce_api.entity.product;

import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductConstructorTest {

    @Test
    void allArgsConstructor_setsAllFields() {
        Category category = new Category(1L, "C", false);
        Product product = new Product(
                1L, "n", "d", 4, BigDecimal.valueOf(2.5), "img", null, null, true, category
        );
        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("n");
        assertThat(product.getDescription()).isEqualTo("d");
        assertThat(product.getStock()).isEqualTo(4);
        assertThat(product.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(2.5));
        assertThat(product.getImageUrl()).isEqualTo("img");
        assertThat(product.getDeleted()).isTrue();
        assertThat(product.getCategory()).isSameAs(category);
    }
}
