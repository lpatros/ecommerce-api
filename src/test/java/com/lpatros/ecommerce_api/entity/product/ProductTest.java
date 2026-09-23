package com.lpatros.ecommerce_api.entity.product;

import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    void gettersAndSetters_work() {
        Category category = new Category(1L, "Cat", false);
        Product product = new Product(
                1L, "Widget", "desc", 5, BigDecimal.TEN,
                "img.png", null, null, false, category
        );
        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("Widget");
        assertThat(product.getStock()).isEqualTo(5);
        assertThat(product.getPrice()).isEqualByComparingTo(BigDecimal.TEN);
        assertThat(product.getDeleted()).isFalse();
        assertThat(product.getCategory().getName()).isEqualTo("Cat");

        product.setStock(9);
        product.setName("Renamed");
        assertThat(product.getStock()).isEqualTo(9);
        assertThat(product.getName()).isEqualTo("Renamed");
    }

    @Test
    void noArgsConstructor_exists() {
        Product product = new Product();
        assertThat(product).isNotNull();
    }
}
