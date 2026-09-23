package com.lpatros.ecommerce_api.configuration;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtTokenConfigTest {

    private static final String VALID_SECRET = "this-is-a-very-long-secret-key-32-bytes-minimum!";
    private static final long EXPIRATION_HOURS = 2;

    private JwtTokenConfig jwtTokenConfig;

    @BeforeEach
    void setUp() {
        jwtTokenConfig = new JwtTokenConfig();
        ReflectionTestUtils.setField(jwtTokenConfig, "secret", VALID_SECRET);
        ReflectionTestUtils.setField(jwtTokenConfig, "expiration", EXPIRATION_HOURS);
    }

    @Test
    void validateSecret_passes_whenSecretAtLeast32Bytes() {
        jwtTokenConfig.validateSecret();
    }

    @Test
    void validateSecret_throws_whenSecretTooShort() {
        ReflectionTestUtils.setField(jwtTokenConfig, "secret", "short");
        assertThatThrownBy(jwtTokenConfig::validateSecret)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("32 bytes");
    }

    @Test
    void validateSecret_throws_whenSecretNull() {
        ReflectionTestUtils.setField(jwtTokenConfig, "secret", null);
        assertThatThrownBy(jwtTokenConfig::validateSecret)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void generateToken_thenValidate_returnsSubjectEmail() {
        String token = jwtTokenConfig.generateToken(42L, "user@example.com");
        assertThat(token).isNotBlank();
        String subject = jwtTokenConfig.validateAndGetUsername(token);
        assertThat(subject).isEqualTo("user@example.com");
    }

    @Test
    void validateAndGetUsername_throws_onInvalidToken() {
        assertThatThrownBy(() -> jwtTokenConfig.validateAndGetUsername("not-a-token"))
                .isInstanceOf(JWTVerificationException.class);
    }

    @Test
    void validateAndGetUsername_throws_onTokenSignedWithDifferentSecret() {
        JwtTokenConfig other = new JwtTokenConfig();
        ReflectionTestUtils.setField(other, "secret", "another-completely-different-secret-key-xyz!");
        ReflectionTestUtils.setField(other, "expiration", EXPIRATION_HOURS);
        String token = other.generateToken(1L, "a@b.com");
        assertThatThrownBy(() -> jwtTokenConfig.validateAndGetUsername(token))
                .isInstanceOf(JWTVerificationException.class);
    }
}
