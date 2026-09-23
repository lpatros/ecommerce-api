package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.order.OrderFilter;
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
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.mapper.OrderMapper;
import com.lpatros.ecommerce_api.repository.OrderRepository;
import com.lpatros.ecommerce_api.repository.UserRepository;
import com.lpatros.ecommerce_api.validator.OrderValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock private OrderValidator orderValidator;
    @Mock private OrderRepository orderRepository;
    @Mock private OrderMapper orderMapper;
    @Mock private UserRepository userRepository;

    @InjectMocks private OrderService orderService;

    private User user(Long id) {
        return new User(
                id, "12345678900", "John", "11999999999", "john@example.com",
                "pwd", LocalDate.of(1990, 1, 1), null, List.of(),
                null, false, "USER"
        );
    }

    private Product product(Long id, Integer stock) {
        Product p = new Product();
        p.setId(id);
        p.setName("Widget");
        p.setStock(stock);
        p.setPrice(BigDecimal.TEN);
        p.setImageUrl("img.png");
        p.setDeleted(false);
        p.setCategory(new Category(1L, "Cat", false));
        return p;
    }

    private Order orderWithStock(Long id, Product product, int quantity) {
        OrderItem item = new OrderItem(1L, quantity, BigDecimal.TEN, product, null, false);
        Order order = new Order(id, List.of(item), new BigDecimal("20.00"), OrderStatus.PENDING,
                null, user(1L), null, false);
        item.setOrder(order);
        return order;
    }

    @Test
    void findById_returnsMappedResponse() {
        Order order = new Order();
        order.setId(1L);
        OrderResponse expected = new OrderResponse();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.toResponse(order)).thenReturn(expected);
        assertThat(orderService.findById(1L)).isEqualTo(expected);
    }

    @Test
    void findById_throwsNotFound_whenMissing() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderService.findById(99L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void create_throwsNotFound_whenUserMissing() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        OrderRequest request = new OrderRequest(List.of(), OrderStatus.PENDING, null);
        assertThatThrownBy(() -> orderService.create(request, 1L))
                .isInstanceOf(NotFoundException.class);
        verify(orderValidator, never()).validateCreate(any());
    }

    @Test
    void create_decreasesStockAndSaves() {
        User u = user(1L);
        Product p = product(1L, 10);
        Order order = orderWithStock(null, p, 3);
        OrderResponse expected = new OrderResponse();
        OrderRequest request = new OrderRequest(List.of(new OrderItemRequest(3, 1L)), OrderStatus.PENDING, null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(u));
        when(orderMapper.toEntity(request, u)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toResponse(order)).thenReturn(expected);

        assertThat(orderService.create(request, 1L)).isEqualTo(expected);
        assertThat(p.getStock()).isEqualTo(7);
        verify(orderValidator).validateCreate(request);
    }

    @Test
    void partialUpdate_throwsNotFound_whenMissing() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderService.partialUpdate(1L, new OrderPatch()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void partialUpdate_savesPatchedOrder() {
        Order order = new Order();
        order.setId(1L);
        OrderPatch patch = new OrderPatch(OrderStatus.SHIPPED, "TRK");
        OrderResponse expected = new OrderResponse();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toResponse(order)).thenReturn(expected);
        assertThat(orderService.partialUpdate(1L, patch)).isEqualTo(expected);
        verify(orderMapper).updateEntityFromPatch(order, patch);
    }

    @Test
    void isOrderOwner_delegatesToRepository() {
        when(orderRepository.existsByIdAndUserId(1L, 2L)).thenReturn(true);
        assertThat(orderService.isOrderOwner(1L, 2L)).isTrue();
        when(orderRepository.existsByIdAndUserId(1L, 3L)).thenReturn(false);
        assertThat(orderService.isOrderOwner(1L, 3L)).isFalse();
    }

    @Test
    void findAll_returnsMappedPagination() {
        Order order = new Order();
        order.setId(1L);
        OrderResponse or = new OrderResponse();
        var page = new PageImpl<>(List.of(order), PageRequest.of(0, 10), 1);
        when(orderRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(page);
        when(orderMapper.toResponsePagination(page)).thenReturn(new Pagination<>(page.map(o -> or)));
        Pagination<OrderResponse> result = orderService.findAll(new OrderFilter(), PageRequest.of(0, 10));
        assertThat(result.getContent()).containsExactly(or);
    }
}
