package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.dto.order.orderItem.OrderItemRequest;
import com.lpatros.ecommerce_api.dto.order.orderItem.OrderItemResponse;
import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.entity.Product;
import com.lpatros.ecommerce_api.entity.order.Order;
import com.lpatros.ecommerce_api.entity.order.OrderItem;
import com.lpatros.ecommerce_api.entity.order.OrderStatus;
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemMapperTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderItemMapper orderItemMapper;

    private Product product(Long id, BigDecimal price) {
        Product p = new Product();
        p.setId(id);
        p.setName("Widget");
        p.setStock(10);
        p.setPrice(price);
        p.setImageUrl("img.png");
        p.setDeleted(false);
        p.setCategory(new Category(1L, "Cat", false));
        return p;
    }

    private Order order() {
        return new Order(1L, null, BigDecimal.ZERO, OrderStatus.PENDING, null, null, null, false);
    }

    @Test
    void toOrderItemResponse_mapsFields() {
        Product p = product(7L, BigDecimal.TEN);
        OrderItem item = new OrderItem(3L, 2, BigDecimal.TEN, p, order(), false);
        OrderItemResponse response = orderItemMapper.toOrderItemResponse(item);
        assertThat(response.getId()).isEqualTo(3L);
        assertThat(response.getQuantity()).isEqualTo(2);
        assertThat(response.getUnitPrice()).isEqualByComparingTo(BigDecimal.TEN);
        assertThat(response.getProductId()).isEqualTo(7L);
    }

    @Test
    void toEntity_setsUnitPriceFromProductPrice() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product(1L, BigDecimal.valueOf(49.90))));
        OrderItem item = orderItemMapper.toEntity(new OrderItemRequest(3, 1L), order());
        assertThat(item.getUnitPrice()).isEqualByComparingTo(BigDecimal.valueOf(49.90));
        assertThat(item.getQuantity()).isEqualTo(3);
        assertThat(item.getDeleted()).isFalse();
        assertThat(item.getProduct()).isNotNull();
    }

    @Test
    void toEntity_throwsNotFound_whenProductMissing() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderItemMapper.toEntity(new OrderItemRequest(1, 99L), order()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void toEntityList_mapsAll() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product(1L, BigDecimal.TEN)));
        List<OrderItem> items = orderItemMapper.toEntityList(
                List.of(new OrderItemRequest(1, 1L), new OrderItemRequest(2, 1L)), order());
        assertThat(items).hasSize(2);
    }
}
