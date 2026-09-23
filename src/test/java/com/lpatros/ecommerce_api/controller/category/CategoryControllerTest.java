package com.lpatros.ecommerce_api.controller.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.category.CategoryFilter;
import com.lpatros.ecommerce_api.dto.category.CategoryRequest;
import com.lpatros.ecommerce_api.dto.category.CategoryResponse;
import com.lpatros.ecommerce_api.exception.GlobalExceptionHandler;
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.service.CategoryService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock private CategoryService categoryService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        CategoryController controller = new CategoryController(categoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler(mock(MessageSource.class)))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    private CategoryResponse response(Long id, String name) {
        CategoryResponse r = new CategoryResponse();
        r.setId(id);
        r.setName(name);
        return r;
    }

    @Test
    void findAll_returns200WithPagination() throws Exception {
        CategoryResponse r = response(1L, "Electronics");
        var page = new PageImpl<>(List.of(r), PageRequest.of(0, 10), 1);
        when(categoryService.findAll(any(CategoryFilter.class), any()))
                .thenReturn(new Pagination<>(page));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Electronics"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void findById_returns200() throws Exception {
        when(categoryService.findById(1L)).thenReturn(response(1L, "Books"));

        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Books"));
    }

    @Test
    void findById_returns404_whenMissing() throws Exception {
        when(categoryService.findById(99L)).thenThrow(new NotFoundException("Category", "id"));

        mockMvc.perform(get("/categories/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_returns200() throws Exception {
        CategoryRequest request = new CategoryRequest("Books");
        when(categoryService.create(any(CategoryRequest.class))).thenReturn(response(1L, "Books"));

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Books"));
    }

    @Test
    void create_returns400_whenNameBlank() throws Exception {
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_returns200() throws Exception {
        when(categoryService.update(eq(1L), any(CategoryRequest.class)))
                .thenReturn(response(1L, "Updated"));

        mockMvc.perform(put("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void delete_returns204() throws Exception {
        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isNoContent());
        verify(categoryService).delete(1L);
    }

    @Test
    void delete_returns404_whenMissing() throws Exception {
        org.mockito.Mockito.doThrow(new NotFoundException("Category", "id"))
                .when(categoryService).delete(99L);

        mockMvc.perform(delete("/categories/99"))
                .andExpect(status().isNotFound());
    }
}
