package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.user.UserFilter;
import com.lpatros.ecommerce_api.dto.user.UserPatch;
import com.lpatros.ecommerce_api.dto.user.UserRequest;
import com.lpatros.ecommerce_api.dto.user.UserResponse;
import com.lpatros.ecommerce_api.entity.User;
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.mapper.UserMapper;
import com.lpatros.ecommerce_api.repository.UserRepository;
import com.lpatros.ecommerce_api.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserValidator userValidator;
    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;

    @InjectMocks private UserService userService;

    private User user(Long id) {
        return new User(
                id, "12345678900", "John", "11999999999", "john@example.com",
                "pwd", LocalDate.of(1990, 1, 1), null, List.of(),
                LocalDateTime.now(), false, "USER"
        );
    }

    @Test
    void findById_returnsMappedResponse() {
        User u = user(1L);
        UserResponse expected = new UserResponse();
        when(userRepository.findById(1L)).thenReturn(Optional.of(u));
        when(userMapper.toResponse(u)).thenReturn(expected);
        assertThat(userService.findById(1L)).isEqualTo(expected);
    }

    @Test
    void findById_throwsNotFound_whenMissing() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.findById(99L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void create_validatesAndSaves() {
        UserRequest request = new UserRequest();
        User u = user(null);
        UserResponse response = new UserResponse();
        when(userMapper.toEntity(request)).thenReturn(u);
        when(userRepository.save(u)).thenReturn(u);
        when(userMapper.toResponse(u)).thenReturn(response);
        assertThat(userService.create(request)).isEqualTo(response);
        verify(userValidator).validateCreate(request);
    }

    @Test
    void create_skipsMapper_whenValidationFails() {
        UserRequest request = new UserRequest();
        org.mockito.Mockito.doThrow(new com.lpatros.ecommerce_api.exception.NotUniqueException("User", "CPF"))
                .when(userValidator).validateCreate(request);
        assertThatThrownBy(() -> userService.create(request))
                .isInstanceOf(com.lpatros.ecommerce_api.exception.NotUniqueException.class);
        verify(userMapper, never()).toEntity(any());
    }

    @Test
    void update_throwsNotFound_whenMissing() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.update(1L, new UserRequest()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void update_preservesIdCreatedAtAndRole() {
        User existing = user(1L);
        existing.setRole("ADMIN");
        UserRequest request = new UserRequest();
        User mapped = user(null);
        mapped.setRole("USER");
        UserResponse expected = new UserResponse();

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userMapper.toEntity(request)).thenReturn(mapped);
        when(userRepository.save(mapped)).thenReturn(mapped);
        when(userMapper.toResponse(mapped)).thenReturn(expected);

        assertThat(userService.update(1L, request)).isEqualTo(expected);
        assertThat(mapped.getId()).isEqualTo(1L);
        assertThat(mapped.getCreatedAt()).isEqualTo(existing.getCreatedAt());
        assertThat(mapped.getRole()).isEqualTo("ADMIN");
    }

    @Test
    void partialUpdate_throwsNotFound_whenMissing() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.partialUpdate(1L, new UserPatch()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void delete_callsRepository_whenExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user(1L)));
        userService.delete(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void delete_throwsNotFound_whenMissing() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.delete(1L)).isInstanceOf(NotFoundException.class);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void findAll_returnsMappedPagination() {
        User u = user(1L);
        UserResponse ur = new UserResponse();
        Page<User> page = new PageImpl<>(List.of(u), PageRequest.of(0, 10), 1);
        when(userRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(page);
        when(userMapper.toResponsePagination(page)).thenReturn(new Pagination<>(page.map(x -> ur)));
        Pagination<UserResponse> result = userService.findAll(new UserFilter(), PageRequest.of(0, 10));
        assertThat(result.getContent()).containsExactly(ur);
    }
}
