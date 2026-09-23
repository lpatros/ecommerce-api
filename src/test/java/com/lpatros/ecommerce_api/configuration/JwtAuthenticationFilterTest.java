package com.lpatros.ecommerce_api.configuration;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock private JwtTokenConfig jwtTokenConfig;
    @Mock private UserDetailsService userDetailsService;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private FilterChain filterChain;

    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        filter = new JwtAuthenticationFilter(jwtTokenConfig, userDetailsService);
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private UserDetails userDetails() {
        UserDetails ud = mock(UserDetails.class);
        lenient().when(ud.getAuthorities()).thenReturn(List.of());
        return ud;
    }

    @Test
    void doFilterInternal_setsAuthentication_whenValidBearerToken() throws ServletException, IOException {
        UserDetails ud = userDetails();
        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");
        when(jwtTokenConfig.validateAndGetUsername("valid-token")).thenReturn("a@b.com");
        when(userDetailsService.loadUserByUsername("a@b.com")).thenReturn(ud);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        SecurityContextHolder.getContext().getAuthentication();
        org.assertj.core.api.Assertions
                .assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
    }

    @Test
    void doFilterInternal_continuesWithoutAuth_whenNoAuthorizationHeader() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        org.assertj.core.api.Assertions
                .assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(jwtTokenConfig, never()).validateAndGetUsername(anyString());
    }

    @Test
    void doFilterInternal_continuesWithoutAuth_whenHeaderNotBearer() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Basic abc");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(jwtTokenConfig, never()).validateAndGetUsername(anyString());
    }

    @Test
    void doFilterInternal_continuesWithoutAuth_whenTokenInvalid() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer bad-token");
        when(jwtTokenConfig.validateAndGetUsername("bad-token"))
                .thenThrow(new JWTVerificationException("invalid"));

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        org.assertj.core.api.Assertions
                .assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void doFilterInternal_continuesWithoutAuth_whenUserLookupFails() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtTokenConfig.validateAndGetUsername("token")).thenReturn("missing@x.com");
        when(userDetailsService.loadUserByUsername("missing@x.com"))
                .thenThrow(new RuntimeException("not found"));

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        org.assertj.core.api.Assertions
                .assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void extractTokenFromRequest_returnsNull_whenNoHeader() {
        when(request.getHeader("Authorization")).thenReturn(null);
        Object token = ReflectionTestUtils.invokeMethod(filter, "extractTokenFromRequest", request);
        org.assertj.core.api.Assertions.assertThat(token).isNull();
    }

    @Test
    void extractTokenFromRequest_returnsToken_whenBearerPresent() {
        when(request.getHeader("Authorization")).thenReturn("Bearer abc.def.ghi");
        Object token = ReflectionTestUtils.invokeMethod(filter, "extractTokenFromRequest", request);
        org.assertj.core.api.Assertions.assertThat(token).isEqualTo("abc.def.ghi");
    }
}
