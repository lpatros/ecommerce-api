package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.order.OrderFilter;
import com.lpatros.ecommerce_api.dto.order.OrderRequest;
import com.lpatros.ecommerce_api.dto.order.OrderResponse;
import com.lpatros.ecommerce_api.entity.User;
import com.lpatros.ecommerce_api.entity.order.Order;
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.mapper.OrderMapper;
import com.lpatros.ecommerce_api.repository.OrderRepository;
import com.lpatros.ecommerce_api.repository.UserRepository;
import com.lpatros.ecommerce_api.repository.specification.OrderSpecification;
import com.lpatros.ecommerce_api.validator.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderValidator orderValidator;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;

    @Autowired
    public OrderService(OrderValidator orderValidator, OrderRepository orderRepository, OrderMapper orderMapper, UserRepository userRepository) {
        this.orderValidator = orderValidator;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
    }

    public Pagination<OrderResponse> findAll(OrderFilter orderFilter, Pageable pageable) {

        Specification<Order> specification = OrderSpecification.filter(orderFilter);

        Page<Order> orders = orderRepository.findAll(specification, pageable);

        return orderMapper.toResponsePagination(orders);
    }

    public OrderResponse findById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order", "id"));

        return orderMapper.toResponse(order);
    }

    public OrderResponse create(OrderRequest orderRequest) {

        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("User", "id"));

        orderValidator.validateCreate(orderRequest);

        Order order = orderMapper.toEntity(orderRequest, user);

        return orderMapper.toResponse(orderRepository.save(order));
    }
}
