package com.lpatros.ecommerce_api.configuration;

import com.lpatros.ecommerce_api.entity.User;
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthConfigTest {

    @Mock private UserRepository userRepository;

    @InjectMocks private AuthConfig authConfig;

    private User user() {
        return new User(
                1L, "123", "John", "119", "john@example.com",
                "pwd", LocalDate.of(1990, 1, 1), null, List.of(),
                null, false, "USER"
        );
    }

    @Test
    void loadUserByUsername_returnsUser_whenFound() {
        User u = user();
        when(userRepository.findUserByEmail("john@example.com")).thenReturn(Optional.of(u));

        assertThat(authConfig.loadUserByUsername("john@example.com")).isEqualTo(u);
    }

    @Test
    void loadUserByUsername_throwsNotFound_whenMissing() {
        when(userRepository.findUserByEmail("missing@x.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authConfig.loadUserByUsername("missing@x.com"))
                .isInstanceOf(NotFoundException.class);
    }
}
