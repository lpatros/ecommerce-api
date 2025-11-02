package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.dto.order.orderItem.OrderItemRequest;
import com.lpatros.ecommerce_api.dto.order.orderItem.OrderItemResponse;
import com.lpatros.ecommerce_api.entity.Product;
import com.lpatros.ecommerce_api.entity.order.Order;
import com.lpatros.ecommerce_api.entity.order.OrderItem;
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemMapper {

    private final ProductRepository productRepository;

    @Autowired
    public OrderItemMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        return new OrderItemResponse(
            orderItem.getId(),
            orderItem.getQuantity(),
            orderItem.getUnitPrice(),
            orderItem.getProduct().getId()
        );
    }

    public List<OrderItemResponse> toResponseList(List<OrderItem> orderItems) {
        return orderItems.stream()
            .map(this::toOrderItemResponse)
            .toList();
    }

    public OrderItem toEntity(OrderItemRequest orderItemRequest, Order order) {

        Product product = productRepository.findById(orderItemRequest.getProductId())
                .orElseThrow(() -> new NotFoundException("Product", "id"));

        return new OrderItem(
            null,
            orderItemRequest.getQuantity(),
            orderItemRequest.getUnitPrice(),
            product,
            order,
            Boolean.FALSE
        );
    }

    public List<OrderItem> toEntityList(List<OrderItemRequest> orderItemRequests, Order order) {
        return orderItemRequests.stream()
            .map(request -> toEntity(request, order))
            .toList();
    }
}
