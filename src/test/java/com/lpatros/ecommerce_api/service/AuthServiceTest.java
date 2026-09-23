package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.configuration.JwtTokenConfig;
import com.lpatros.ecommerce_api.dto.auth.LoginRequest;
import com.lpatros.ecommerce_api.dto.auth.LoginResponse;
import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private AuthenticationManager authenticationManager;
    @Mock private JwtTokenConfig jwtTokenConfig;

    @InjectMocks private AuthService authService;

    private User user() {
        return new User(
                7L, "12345678900", "John", "11999999999", "john@example.com",
                "pwd", LocalDate.of(1990, 1, 1), null, List.of(),
                null, false, "USER"
        );
    }

    @Test
    void login_returnsToken_whenAuthenticationSucceeds() {
        LoginRequest request = new LoginRequest("john@example.com", "secret");
        Authentication auth = new UsernamePasswordAuthenticationToken(user(), null, List.of());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);
        when(jwtTokenConfig.generateToken(7L, "john@example.com")).thenReturn("jwt-token");

        LoginResponse response = authService.login(request);

        assertThat(response.getToken()).isEqualTo("jwt-token");
    }

    @Test
    void login_passesEmailAndPasswordToAuthenticationManager() {
        LoginRequest request = new LoginRequest("john@example.com", "secret");
        Authentication auth = new UsernamePasswordAuthenticationToken(user(), null, List.of());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);
        when(jwtTokenConfig.generateToken(eq(7L), eq("john@example.com"))).thenReturn("t");

        authService.login(request);

        org.mockito.Mockito.verify(authenticationManager)
                .authenticate(org.mockito.ArgumentMatchers.argThat(token ->
                        "john@example.com".equals(token.getPrincipal()) &&
                        "secret".equals(token.getCredentials())));
    }
}
