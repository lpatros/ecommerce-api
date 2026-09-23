package com.lpatros.ecommerce_api.validator;

import com.lpatros.ecommerce_api.dto.order.OrderRequest;
import com.lpatros.ecommerce_api.dto.order.orderItem.OrderItemRequest;
import com.lpatros.ecommerce_api.exception.DuplicateItemsListException;
import com.lpatros.ecommerce_api.exception.NotMatchException;
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        validateStock(orderRequest.getOrderItems());
    }

    public void validateDuplicateItemsList(List<OrderItemRequest> orderItems) {
        Set<Long> productIds = new HashSet<>();

        for (OrderItemRequest item : orderItems) {
            if (!productIds.add(item.getProductId())) {
                throw new DuplicateItemsListException("Product");
            }
        }
    }

    public void validateStock(List<OrderItemRequest> orderItems) {
        for (OrderItemRequest item : orderItems) {
            var product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product", "id"));

            if (product.getStock() == null || product.getStock() < item.getQuantity()) {
                throw new NotMatchException("Requested quantity", "available stock");
            }
        }
    }
}
