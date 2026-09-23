package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.dto.user.UserPatch;
import com.lpatros.ecommerce_api.dto.user.UserRequest;
import com.lpatros.ecommerce_api.dto.user.UserResponse;
import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        OrderItemMapper orderItemMapper = new OrderItemMapper(null);
        OrderMapper orderMapper = new OrderMapper(orderItemMapper);
        userMapper = new UserMapper(orderMapper, passwordEncoder);
    }

    private UserRequest request() {
        UserRequest r = new UserRequest();
        r.setCpf("12345678900");
        r.setName("John");
        r.setPhoneNumber("11999999999");
        r.setEmail("john@example.com");
        r.setPassword("secret");
        r.setConfirmPassword("secret");
        r.setBirthDate(LocalDate.of(1990, 1, 1));
        r.setAddress("Street 1");
        return r;
    }

    @Test
    void toEntity_encodesPasswordAndSetsRoleUser() {
        when(passwordEncoder.encode("secret")).thenReturn("encoded");
        User user = userMapper.toEntity(request());
        assertThat(user.getPassword()).isEqualTo("encoded");
        assertThat(user.getRole()).isEqualTo("USER");
        assertThat(user.getDeleted()).isFalse();
        assertThat(user.getOrders()).isEmpty();
        verify(passwordEncoder).encode("secret");
    }

    @Test
    void toResponse_mapsFields() {
        User user = new User(
                1L, "12345678900", "John", "11999999999", "john@example.com",
                "pwd", LocalDate.of(1990, 1, 1), "Addr", List.of(), null, false, "USER"
        );
        UserResponse response = userMapper.toResponse(user);
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getEmail()).isEqualTo("john@example.com");
        assertThat(response.getOrders()).isEmpty();
    }

    @Test
    void updateEntityFromPatch_updatesNonNullFields() {
        User user = new User(
                1L, "cpf", "Old", "111", "old@example.com",
                "oldPwd", LocalDate.of(1990, 1, 1), null, List.of(), null, false, "USER"
        );
        UserPatch patch = new UserPatch();
        patch.setName("New");
        patch.setEmail("new@example.com");
        userMapper.updateEntityFromPatch(user, patch);
        assertThat(user.getName()).isEqualTo("New");
        assertThat(user.getEmail()).isEqualTo("new@example.com");
        assertThat(user.getPassword()).isEqualTo("oldPwd");
    }

    @Test
    void updateEntityFromPatch_reencodesPassword_whenBothProvided() {
        when(passwordEncoder.encode("newpass")).thenReturn("re-encoded");
        User user = new User(
                1L, "cpf", "John", "111", "john@example.com",
                "old", LocalDate.of(1990, 1, 1), null, List.of(), null, false, "USER"
        );
        UserPatch patch = new UserPatch();
        patch.setPassword("newpass");
        patch.setConfirmPassword("newpass");
        userMapper.updateEntityFromPatch(user, patch);
        assertThat(user.getPassword()).isEqualTo("re-encoded");
    }

    @Test
    void updateEntityFromPatch_passwordOnly_doesNotReencode() {
        User user = new User(
                1L, "cpf", "John", "111", "john@example.com",
                "old", LocalDate.of(1990, 1, 1), null, List.of(), null, false, "USER"
        );
        UserPatch patch = new UserPatch();
        patch.setPassword("newpass");
        patch.setConfirmPassword(null);
        userMapper.updateEntityFromPatch(user, patch);
        assertThat(user.getPassword()).isEqualTo("old");
    }
}
