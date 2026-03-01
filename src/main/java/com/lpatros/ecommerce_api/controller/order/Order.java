package com.lpatros.ecommerce_api.controller.order;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.order.OrderFilter;
import com.lpatros.ecommerce_api.dto.order.OrderPatch;
import com.lpatros.ecommerce_api.dto.order.OrderRequest;
import com.lpatros.ecommerce_api.dto.order.OrderResponse;
import com.lpatros.ecommerce_api.entity.User;
import com.lpatros.ecommerce_api.exception.RestErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Order")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("orders")
public interface Order {

    @Operation(summary = "Get all Orders with filters", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pagination.class)))
    })
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #orderFilter.userId == principal.id)")
    @GetMapping
    ResponseEntity<Pagination<OrderResponse>> findAll(@ModelAttribute OrderFilter orderFilter, Pageable pageable);

    @Operation(summary = "Get Order by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class)))
    })
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @orderService.isOrderOwner(#id, principal.id))")
    @GetMapping("/{id}")
    ResponseEntity<OrderResponse> findById(@PathVariable Long id);

    @Operation(summary = "Create a new Order", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class)))
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    ResponseEntity<OrderResponse> create(@RequestBody @Valid OrderRequest orderRequest, @AuthenticationPrincipal User user);

    @Operation(summary = "Partially update a Order by Id", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    ResponseEntity<OrderResponse> partialUpdate(@PathVariable Long id, @RequestBody @Valid OrderPatch orderPatch);
}
