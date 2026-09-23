package com.lpatros.ecommerce_api.validator;

import com.lpatros.ecommerce_api.dto.order.OrderRequest;
import com.lpatros.ecommerce_api.dto.order.orderItem.OrderItemRequest;
import com.lpatros.ecommerce_api.entity.Product;
import com.lpatros.ecommerce_api.entity.order.OrderStatus;
import com.lpatros.ecommerce_api.exception.DuplicateItemsListException;
import com.lpatros.ecommerce_api.exception.NotMatchException;
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

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderValidatorTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderValidator orderValidator;

    private Product product(Integer stock) {
        Product p = new Product();
        p.setId(1L);
        p.setName("Widget");
        p.setStock(stock);
        p.setPrice(BigDecimal.TEN);
        p.setImageUrl("img.png");
        p.setDeleted(false);
        return p;
    }

    private OrderItemRequest item(Long productId, Integer quantity) {
        return new OrderItemRequest(quantity, productId);
    }

    private OrderRequest request(List<OrderItemRequest> items) {
        return new OrderRequest(items, OrderStatus.PENDING, null);
    }

    @Test
    void validateDuplicateItemsList_throws_whenProductIdRepeats() {
        List<OrderItemRequest> items = List.of(item(1L, 1), item(1L, 2));
        assertThatThrownBy(() -> orderValidator.validateDuplicateItemsList(items))
                .isInstanceOf(DuplicateItemsListException.class)
                .hasMessageContaining("Product");
    }

    @Test
    void validateDuplicateItemsList_passes_whenProductIdsDistinct() {
        List<OrderItemRequest> items = List.of(item(1L, 1), item(2L, 2));
        assertThatCode(() -> orderValidator.validateDuplicateItemsList(items))
                .doesNotThrowAnyException();
    }

    @Test
    void validateStock_throwsNotFound_whenProductMissing() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderValidator.validateStock(List.of(item(9L, 1))))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void validateStock_throwsNotMatch_whenInsufficientStock() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product(2)));
        assertThatThrownBy(() -> orderValidator.validateStock(List.of(item(1L, 5))))
                .isInstanceOf(NotMatchException.class);
    }

    @Test
    void validateStock_throwsNotMatch_whenStockNull() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product(null)));
        assertThatThrownBy(() -> orderValidator.validateStock(List.of(item(1L, 1))))
                .isInstanceOf(NotMatchException.class);
    }

    @Test
    void validateStock_passes_whenStockSufficient() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product(10)));
        assertThatCode(() -> orderValidator.validateStock(List.of(item(1L, 5))))
                .doesNotThrowAnyException();
    }

    @Test
    void validateCreate_passes_whenValidRequest() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product(10)));
        assertThatCode(() -> orderValidator.validateCreate(
                request(List.of(item(1L, 3)))))
                .doesNotThrowAnyException();
    }
}
