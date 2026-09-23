package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.dto.product.ProductPatch;
import com.lpatros.ecommerce_api.dto.product.ProductRequest;
import com.lpatros.ecommerce_api.dto.product.ProductResponse;
import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.entity.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {

    private final CategoryMapper categoryMapper = new CategoryMapper();
    private final ProductMapper productMapper = new ProductMapper(categoryMapper);

    private Category category() {
        return new Category(1L, "Electronics", false);
    }

    @Test
    void toResponse_mapsFieldsIncludingCategory() {
        Product product = new Product(
                1L, "Phone", "A phone", 10, BigDecimal.valueOf(999.99),
                "img.png", null, null, false, category()
        );
        ProductResponse response = productMapper.toResponse(product);
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Phone");
        assertThat(response.getStock()).isEqualTo(10);
        assertThat(response.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(999.99));
        assertThat(response.getCategory().getName()).isEqualTo("Electronics");
    }

    @Test
    void toEntity_mapsRequestAndNotDeleted() {
        ProductRequest request = new ProductRequest("Laptop", "desc", 5, BigDecimal.TEN, "img.png", 1L);
        Product product = productMapper.toEntity(request, category());
        assertThat(product.getId()).isNull();
        assertThat(product.getName()).isEqualTo("Laptop");
        assertThat(product.getStock()).isEqualTo(5);
        assertThat(product.getDeleted()).isFalse();
        assertThat(product.getCategory().getId()).isEqualTo(1L);
    }

    @Test
    void updateEntityFromPatch_updatesNonNullFieldsOnly() {
        Product product = new Product(
                1L, "Old", "oldDesc", 1, BigDecimal.ONE,
                "old.png", null, null, false, category()
        );
        ProductPatch patch = new ProductPatch();
        patch.setName("New");
        patch.setStock(42);

        productMapper.updateEntityFromPatch(product, patch);

        assertThat(product.getName()).isEqualTo("New");
        assertThat(product.getStock()).isEqualTo(42);
        assertThat(product.getDescription()).isEqualTo("oldDesc");
        assertThat(product.getPrice()).isEqualByComparingTo(BigDecimal.ONE);
        assertThat(product.getImageUrl()).isEqualTo("old.png");
    }

    @Test
    void updateEntityFromPatch_allNull_noChanges() {
        Product product = new Product(
                1L, "Same", null, 3, BigDecimal.TEN,
                "img", null, null, false, category()
        );
        productMapper.updateEntityFromPatch(product, new ProductPatch());
        assertThat(product.getName()).isEqualTo("Same");
        assertThat(product.getStock()).isEqualTo(3);
    }
}
