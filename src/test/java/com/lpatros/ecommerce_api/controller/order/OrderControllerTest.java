package com.lpatros.ecommerce_api.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.order.OrderFilter;
import com.lpatros.ecommerce_api.dto.order.OrderPatch;
import com.lpatros.ecommerce_api.dto.order.OrderRequest;
import com.lpatros.ecommerce_api.dto.order.OrderResponse;
import com.lpatros.ecommerce_api.entity.User;
import com.lpatros.ecommerce_api.entity.order.OrderStatus;
import com.lpatros.ecommerce_api.exception.GlobalExceptionHandler;
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock private OrderService orderService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        OrderController controller = new OrderController(orderService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler(mock(MessageSource.class)))
                .setCustomArgumentResolvers(
                        new AuthenticationPrincipalArgumentResolver(),
                        new PageableHandlerMethodArgumentResolver())
                .build();

        User user = new User(
                1L, "12345678900", "John", "11999999999", "john@example.com",
                "pwd", LocalDate.of(1990, 1, 1), null, List.of(),
                null, false, "USER"
        );
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private OrderResponse response(Long id) {
        OrderResponse r = new OrderResponse();
        r.setId(id);
        r.setTotalPrice(BigDecimal.TEN);
        r.setStatus(OrderStatus.PENDING.name());
        r.setUserId(1L);
        return r;
    }

    private String validRequestJson() {
        return """
                {"orderItems":[{"quantity":2,"productId":1}],"status":"PENDING","trackingCode":null}
                """;
    }

    @Test
    void findAll_returns200WithPagination() throws Exception {
        OrderResponse r = response(1L);
        var page = new PageImpl<>(List.of(r), PageRequest.of(0, 10), 1);
        when(orderService.findAll(any(OrderFilter.class), any())).thenReturn(new Pagination<>(page));

        mockMvc.perform(get("/orders").param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1));
    }

    @Test
    void findById_returns200() throws Exception {
        when(orderService.findById(1L)).thenReturn(response(1L));

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void findById_returns404_whenMissing() throws Exception {
        when(orderService.findById(99L)).thenThrow(new NotFoundException("Order", "id"));

        mockMvc.perform(get("/orders/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_returns201() throws Exception {
        when(orderService.create(any(OrderRequest.class), eq(1L))).thenReturn(response(1L));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void create_returns400_whenOrderItemsMissing() throws Exception {
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"PENDING\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void partialUpdate_returns200() throws Exception {
        when(orderService.partialUpdate(eq(1L), any(OrderPatch.class))).thenReturn(response(1L));

        mockMvc.perform(patch("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderStatus\":\"SHIPPED\",\"trackingCode\":\"TRK\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void partialUpdate_returns404_whenMissing() throws Exception {
        when(orderService.partialUpdate(eq(99L), any(OrderPatch.class)))
                .thenThrow(new NotFoundException("Order", "id"));

        mockMvc.perform(patch("/orders/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderStatus\":\"SHIPPED\"}"))
                .andExpect(status().isNotFound());
    }
}
