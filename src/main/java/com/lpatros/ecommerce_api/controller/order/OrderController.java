package com.lpatros.ecommerce_api.controller.order;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.order.OrderFilter;
import com.lpatros.ecommerce_api.dto.order.OrderPatch;
import com.lpatros.ecommerce_api.dto.order.OrderRequest;
import com.lpatros.ecommerce_api.dto.order.OrderResponse;
import com.lpatros.ecommerce_api.entity.User;
import com.lpatros.ecommerce_api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements Order {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public ResponseEntity<Pagination<OrderResponse>> findAll(OrderFilter orderFilter, Pageable pageable) {
        return ResponseEntity.ok(orderService.findAll(orderFilter, pageable));
    }

    public ResponseEntity<OrderResponse> findById(Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    public ResponseEntity<OrderResponse> create(OrderRequest orderRequest, User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(orderRequest, user.getId()));
    }

    public ResponseEntity<OrderResponse> partialUpdate(Long id, OrderPatch orderPatch) {
        return ResponseEntity.ok(orderService.partialUpdate(id, orderPatch));
    }
}
