package com.lpatros.ecommerce_api.validator;

import com.lpatros.ecommerce_api.dto.order.OrderRequest;
import com.lpatros.ecommerce_api.dto.order.orderItem.OrderItemRequest;
import com.lpatros.ecommerce_api.exception.DuplicateItemsListException;
import com.lpatros.ecommerce_api.exception.NotMatchException;
import com.lpatros.ecommerce_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class OrderValidator {

    private final ProductRepository productRepository;

    @Autowired
    public OrderValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void validateCreate(OrderRequest orderRequest) {
        validateDuplicateItemsList(orderRequest.getOrderItems());
        validateTotalPrice(orderRequest);
        validateUnitPrice(orderRequest.getOrderItems());
    }

    public void validateDuplicateItemsList(List<OrderItemRequest> orderItems) {
        Set<Long> productIds = new HashSet<>();

        for (OrderItemRequest item : orderItems) {
            if (!productIds.add(item.getProductId())) {
                throw new DuplicateItemsListException("Product");
            }
        }
    }

    public void validateTotalPrice(OrderRequest orderRequest) {

        BigDecimal totalPrice = orderRequest.getTotalPrice();

        BigDecimal calculatedTotal = orderRequest.getOrderItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalPrice.compareTo(calculatedTotal) != 0) {
            throw new NotMatchException("Total price", "calculated total price");
        }
    }

    public void validateUnitPrice(List<OrderItemRequest> orderItems) {

        for (OrderItemRequest item : orderItems) {
            BigDecimal actualPrice = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"))
                    .getPrice();

            if (item.getUnitPrice().compareTo(actualPrice) != 0) {
                throw new NotMatchException("Price", "actual product price");
            }
        }

    }
}
