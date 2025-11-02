package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.order.OrderRequest;
import com.lpatros.ecommerce_api.dto.order.OrderResponse;
import com.lpatros.ecommerce_api.entity.User;
import com.lpatros.ecommerce_api.entity.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    @Autowired
    public OrderMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    public OrderResponse toResponse(Order order) {
        return new OrderResponse(
            order.getId(),
            orderItemMapper.toResponseList(order.getOrderItems()),
            order.getTotalPrice(),
            order.getStatus().toString(),
            order.getTrackingCode(),
            order.getUser().getId(),
            order.getCreatedAt()
        );
    }

    public Pagination<OrderResponse> toResponsePagination(Page<Order> orders) {
        return Pagination.toPagination(orders.map(this::toResponse));
    }

    public Order toEntity(OrderRequest orderRequest, User user) {

        Order order = new Order(
            null,
            null,
            orderRequest.getTotalPrice(),
            orderRequest.getStatus(),
            orderRequest.getTrackingCode(),
            user,
            LocalDateTime.now(),
            Boolean.FALSE
        );

        order.setOrderItems(orderItemMapper.toEntityList(orderRequest.getOrderItems(), order));

        return order;
    }
}
