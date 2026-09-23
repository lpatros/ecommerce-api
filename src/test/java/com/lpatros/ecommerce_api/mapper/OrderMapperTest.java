package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.dto.order.OrderPatch;
import com.lpatros.ecommerce_api.dto.order.OrderRequest;
import com.lpatros.ecommerce_api.dto.order.OrderResponse;
import com.lpatros.ecommerce_api.dto.order.orderItem.OrderItemRequest;
import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.entity.Product;
import com.lpatros.ecommerce_api.entity.User;
import com.lpatros.ecommerce_api.entity.order.Order;
import com.lpatros.ecommerce_api.entity.order.OrderItem;
import com.lpatros.ecommerce_api.entity.order.OrderStatus;
import com.lpatros.ecommerce_api.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {

    @Mock
    private ProductRepository productRepository;

    private OrderItemMapper orderItemMapper;
    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
        orderItemMapper = new OrderItemMapper(productRepository);
        orderMapper = new OrderMapper(orderItemMapper);
    }

    private User user() {
        return new User(
                1L, "12345678900", "John", "11999999999", "john@example.com",
                "pwd", LocalDate.of(1990, 1, 1), null, List.of(), null, false, "USER"
        );
    }

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

    @Test
    void toEntity_forcesPendingStatusAndComputesTotal() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product(1L, BigDecimal.valueOf(10))));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product(2L, BigDecimal.valueOf(25.50))));

        OrderRequest request = new OrderRequest(
                List.of(new OrderItemRequest(2, 1L), new OrderItemRequest(1, 2L)),
                OrderStatus.DELIVERED,
                "TRACK-1"
        );

        Order order = orderMapper.toEntity(request, user());

        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(order.getTrackingCode()).isEqualTo("TRACK-1");
        // 2*10 + 1*25.50 = 45.50
        assertThat(order.getTotalPrice()).isEqualByComparingTo(new BigDecimal("45.50"));
        assertThat(order.getOrderItems()).hasSize(2);
        assertThat(order.getDeleted()).isFalse();
        assertThat(order.getUser()).isNotNull();
    }

    @Test
    void toEntity_emptyItems_zeroTotal() {
        OrderRequest request = new OrderRequest(List.of(), OrderStatus.PENDING, null);
        Order order = orderMapper.toEntity(request, user());
        assertThat(order.getTotalPrice()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(order.getOrderItems()).isEmpty();
    }

    @Test
    void toResponse_mapsFields() {
        User u = user();
        Product p = product(1L, BigDecimal.TEN);
        Order order = new Order(
                1L,
                List.of(new OrderItem(1L, 2, BigDecimal.TEN, p, null, false)),
                new BigDecimal("20.00"),
                OrderStatus.SHIPPED,
                "TRK",
                u,
                null,
                false
        );
        OrderResponse response = orderMapper.toResponse(order);
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getStatus()).isEqualTo("SHIPPED");
        assertThat(response.getTrackingCode()).isEqualTo("TRK");
        assertThat(response.getUserId()).isEqualTo(1L);
        assertThat(response.getTotalPrice()).isEqualByComparingTo(new BigDecimal("20.00"));
        assertThat(response.getOrderItems()).hasSize(1);
    }

    @Test
    void toResponseList_null_returnsEmpty() {
        assertThat(orderMapper.toResponseList(null)).isEmpty();
    }

    @Test
    void updateEntityFromPatch_updatesStatusAndTracking() {
        Order order = new Order(1L, List.of(), BigDecimal.ZERO, OrderStatus.PENDING, null, user(), null, false);
        OrderPatch patch = new OrderPatch(OrderStatus.SHIPPED, "NEW-TRK");
        orderMapper.updateEntityFromPatch(order, patch);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.SHIPPED);
        assertThat(order.getTrackingCode()).isEqualTo("NEW-TRK");
    }

    @Test
    void updateEntityFromPatch_nullFields_unchanged() {
        Order order = new Order(1L, List.of(), BigDecimal.ZERO, OrderStatus.PENDING, "OLD", user(), null, false);
        orderMapper.updateEntityFromPatch(order, new OrderPatch(null, null));
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(order.getTrackingCode()).isEqualTo("OLD");
    }
}
