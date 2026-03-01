package com.lpatros.ecommerce_api.controller.category;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.category.CategoryFilter;
import com.lpatros.ecommerce_api.dto.category.CategoryRequest;
import com.lpatros.ecommerce_api.dto.category.CategoryResponse;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Category")
@RequestMapping("/categories")
public interface Category {

    @Operation(summary = "Get all Categories with filters", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pagination.class)))
    })
    @GetMapping
    ResponseEntity<Pagination<CategoryResponse>> findAll(@ModelAttribute CategoryFilter categoryFilter, Pageable pageable);

    @Operation(summary = "Get a Category by Id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class)))
    })
    @GetMapping("/{id}")
    ResponseEntity<CategoryResponse> findById(@PathVariable Long id);

    @Operation(summary = "Create a new Category", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class)))
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<CategoryResponse> create(@RequestBody @Valid CategoryRequest categoryRequest);

    @Operation(summary = "Update a Category by Id", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class)))
    })
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<CategoryResponse> update(@PathVariable Long id, @RequestBody @Valid CategoryRequest categoryRequest);

    @Operation(summary = "Delete a Category by Id", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class)))
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
