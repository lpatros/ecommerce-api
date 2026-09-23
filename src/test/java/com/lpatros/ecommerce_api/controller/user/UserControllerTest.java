package com.lpatros.ecommerce_api.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.user.UserFilter;
import com.lpatros.ecommerce_api.dto.user.UserPatch;
import com.lpatros.ecommerce_api.dto.user.UserRequest;
import com.lpatros.ecommerce_api.dto.user.UserResponse;
import com.lpatros.ecommerce_api.exception.GlobalExceptionHandler;
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.service.UserService;
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

import java.time.LocalDate;
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
class UserControllerTest {

    @Mock private UserService userService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        UserController controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler(mock(MessageSource.class)))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    private UserResponse response(Long id, String name) {
        UserResponse r = new UserResponse();
        r.setId(id);
        r.setName(name);
        r.setEmail("user@example.com");
        r.setCpf("12345678900");
        r.setBirthDate(LocalDate.of(1990, 1, 1));
        return r;
    }

    private String validRequestJson() {
        return """
                {"cpf":"12345678900","name":"John","phoneNumber":"11999999999",
                 "email":"john@example.com","password":"secret123","confirmPassword":"secret123",
                 "birthDate":"1990-01-01","address":"Rua A"}
                """;
    }

    @Test
    void findAll_returns200WithPagination() throws Exception {
        UserResponse r = response(1L, "John");
        var page = new PageImpl<>(List.of(r), PageRequest.of(0, 10), 1);
        when(userService.findAll(any(UserFilter.class), any()))
                .thenReturn(new Pagination<>(page));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("John"));
    }

    @Test
    void findById_returns200() throws Exception {
        when(userService.findById(1L)).thenReturn(response(1L, "John"));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void findById_returns404_whenMissing() throws Exception {
        when(userService.findById(99L)).thenThrow(new NotFoundException("User", "id"));

        mockMvc.perform(get("/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_returns200() throws Exception {
        when(userService.create(any(UserRequest.class))).thenReturn(response(1L, "John"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void create_returns400_whenEmailMissing() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cpf\":\"1\",\"name\":\"n\",\"phoneNumber\":\"p\",\"password\":\"p\",\"birthDate\":\"1990-01-01\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_returns200() throws Exception {
        when(userService.update(eq(1L), any(UserRequest.class))).thenReturn(response(1L, "Updated"));

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void partialUpdate_returns200() throws Exception {
        when(userService.partialUpdate(eq(1L), any(UserPatch.class))).thenReturn(response(1L, "Patched"));

        mockMvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Patched\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Patched"));
    }

    @Test
    void partialUpdate_returns404_whenMissing() throws Exception {
        when(userService.partialUpdate(eq(99L), any(UserPatch.class)))
                .thenThrow(new NotFoundException("User", "id"));

        mockMvc.perform(patch("/users/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"x\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_returns204() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
        verify(userService).delete(1L);
    }

    @Test
    void delete_returns404_whenMissing() throws Exception {
        org.mockito.Mockito.doThrow(new NotFoundException("User", "id"))
                .when(userService).delete(99L);

        mockMvc.perform(delete("/users/99"))
                .andExpect(status().isNotFound());
    }
}
