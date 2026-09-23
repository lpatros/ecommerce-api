package com.lpatros.ecommerce_api.util;

import com.lpatros.ecommerce_api.entity.Category;
import com.lpatros.ecommerce_api.entity.Product;
import com.lpatros.ecommerce_api.entity.User;
import com.lpatros.ecommerce_api.entity.order.Order;
import com.lpatros.ecommerce_api.entity.order.OrderItem;
import com.lpatros.ecommerce_api.repository.CategoryRepository;
import com.lpatros.ecommerce_api.repository.OrderRepository;
import com.lpatros.ecommerce_api.repository.ProductRepository;
import com.lpatros.ecommerce_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MockDataTest {

    @Mock private CategoryRepository categoryRepository;
    @Mock private ProductRepository productRepository;
    @Mock private UserRepository userRepository;
    @Mock private OrderRepository orderRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks private MockData mockData;

    @Test
    void run_deletesAll_thenLoadsSeedData() {
        when(passwordEncoder.encode(any())).thenReturn("encoded");

        mockData.run();

        verify(orderRepository).deleteAll();
        verify(productRepository).deleteAll();
        verify(categoryRepository).deleteAll();
        verify(userRepository).deleteAll();

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<Category>> categoryCaptor = ArgumentCaptor.forClass(List.class);
        verify(categoryRepository).saveAll(categoryCaptor.capture());
        assertThat(categoryCaptor.getValue()).hasSize(9);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<Product>> productCaptor = ArgumentCaptor.forClass(List.class);
        verify(productRepository).saveAll(productCaptor.capture());
        assertThat(productCaptor.getValue()).hasSize(90);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<User>> userCaptor = ArgumentCaptor.forClass(List.class);
        verify(userRepository).saveAll(userCaptor.capture());
        assertThat(userCaptor.getValue()).hasSize(2);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<Order>> orderCaptor = ArgumentCaptor.forClass(List.class);
        verify(orderRepository, org.mockito.Mockito.atLeastOnce()).saveAll(orderCaptor.capture());
        List<Order> allOrderSaves = orderCaptor.getAllValues().stream()
                .flatMap(List::stream)
                .filter(o -> o.getOrderItems() != null && !o.getOrderItems().isEmpty())
                .toList();
        assertThat(orderCaptor.getAllValues().getFirst()).hasSize(2);
        assertThat(orderCaptor.getAllValues().getFirst().getFirst().getTotalPrice()).isPositive();
        assertThat(orderCaptor.getAllValues().getFirst().getFirst().getOrderItems()).hasSize(2);
        assertThat(orderCaptor.getAllValues().getFirst().get(1).getOrderItems()).hasSize(1);
        assertThat(orderCaptor.getAllValues().getFirst().get(1).getTotalPrice()).isPositive();
        assertThat(allOrderSaves).isNotEmpty();
    }

    @Test
    void run_swallowsException_whenRepositoryFails() {
        doThrow(new RuntimeException("db down")).when(orderRepository).deleteAll();

        mockData.run();
    }
}
