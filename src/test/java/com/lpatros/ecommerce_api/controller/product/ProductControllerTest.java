package com.lpatros.ecommerce_api.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.product.ProductFilter;
import com.lpatros.ecommerce_api.dto.product.ProductPatch;
import com.lpatros.ecommerce_api.dto.product.ProductRequest;
import com.lpatros.ecommerce_api.dto.product.ProductResponse;
import com.lpatros.ecommerce_api.exception.GlobalExceptionHandler;
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.service.ProductService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock private ProductService productService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        ProductController controller = new ProductController(productService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler(mock(MessageSource.class)))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    private ProductResponse response(Long id, String name) {
        ProductResponse r = new ProductResponse();
        r.setId(id);
        r.setName(name);
        r.setStock(10);
        r.setPrice(BigDecimal.TEN);
        r.setImageUrl("img.png");
        return r;
    }

    private String validRequestJson() {
        return """
                {"name":"Widget","description":"d","stock":5,"price":9.99,"imageUrl":"i.png","categoryId":1}
                """;
    }

    @Test
    void findAll_returns200WithPagination() throws Exception {
        ProductResponse r = response(1L, "Widget");
        var page = new PageImpl<>(List.of(r), PageRequest.of(0, 10), 1);
        when(productService.findAll(any(ProductFilter.class), any()))
                .thenReturn(new Pagination<>(page));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Widget"));
    }

    @Test
    void findById_returns200() throws Exception {
        when(productService.findById(1L)).thenReturn(response(1L, "Widget"));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void findById_returns404_whenMissing() throws Exception {
        when(productService.findById(99L)).thenThrow(new NotFoundException("Product", "id"));

        mockMvc.perform(get("/products/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_returns200() throws Exception {
        when(productService.create(any(ProductRequest.class))).thenReturn(response(1L, "Widget"));

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Widget"));
    }

    @Test
    void create_returns400_whenNameMissing() throws Exception {
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"stock\":1,\"price\":1.0,\"imageUrl\":\"i\",\"categoryId\":1}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_returns200() throws Exception {
        when(productService.update(eq(1L), any(ProductRequest.class)))
                .thenReturn(response(1L, "Updated"));

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void partialUpdate_returns200() throws Exception {
        when(productService.partialUpdate(eq(1L), any(ProductPatch.class)))
                .thenReturn(response(1L, "Patched"));

        mockMvc.perform(patch("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Patched\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Patched"));
    }

    @Test
    void partialUpdate_returns404_whenMissing() throws Exception {
        when(productService.partialUpdate(eq(99L), any(ProductPatch.class)))
                .thenThrow(new NotFoundException("Product", "id"));

        mockMvc.perform(patch("/products/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"x\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_returns204() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
        verify(productService).delete(1L);
    }

    @Test
    void delete_returns404_whenMissing() throws Exception {
        org.mockito.Mockito.doThrow(new NotFoundException("Product", "id"))
                .when(productService).delete(99L);

        mockMvc.perform(delete("/products/99"))
                .andExpect(status().isNotFound());
    }
}
