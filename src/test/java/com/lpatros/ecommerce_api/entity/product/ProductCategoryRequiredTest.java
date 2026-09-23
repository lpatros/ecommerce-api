package com.lpatros.ecommerce_api.entity.product;

import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductCategoryRequiredTest {

    @Test
    void categoryField_mayBeNullBeforeAssociation() {
        Product product = new Product(1L, "n", null, 1, BigDecimal.ONE, "img", null, null, false, null);
        assertThat(product.getCategory()).isNull();
        Category category = new Category(1L, "C", false);
        product.setCategory(category);
        assertThat(product.getCategory().getName()).isEqualTo("C");
    }
}
