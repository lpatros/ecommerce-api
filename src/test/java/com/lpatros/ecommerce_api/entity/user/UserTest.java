package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private User user(Long id, String role, Boolean deleted) {
        return new User(
                id,
                "12345678900",
                "John Doe",
                "11999999999",
                "john@example.com",
                "encoded-password",
                LocalDate.of(1990, 1, 1),
                "Street 1",
                List.of(),
                null,
                deleted,
                role
        );
    }

    @Test
    void isEnabled_returnsTrue_whenDeletedIsNull() {
        User user = user(1L, "USER", null);
        assertThat(user.isEnabled()).isTrue();
    }

    @Test
    void isEnabled_returnsTrue_whenDeletedIsFalse() {
        User user = user(1L, "USER", false);
        assertThat(user.isEnabled()).isTrue();
    }

    @Test
    void isEnabled_returnsFalse_whenDeletedIsTrue() {
        User user = user(1L, "USER", true);
        assertThat(user.isEnabled()).isFalse();
    }

    @Test
    void getAuthorities_returnsPrefixedRole() {
        User user = user(1L, "ADMIN", false);
        List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>) user.getAuthorities();
        assertThat(authorities).hasSize(1);
        assertThat(authorities.getFirst().getAuthority()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    void getAuthorities_defaultsToUser_whenRoleIsNull() {
        User user = user(1L, null, false);
        List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>) user.getAuthorities();
        assertThat(authorities.getFirst().getAuthority()).isEqualTo("ROLE_USER");
    }

    @Test
    void getAuthorities_defaultsToUser_whenRoleIsEmpty() {
        User user = user(1L, "", false);
        List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>) user.getAuthorities();
        assertThat(authorities.getFirst().getAuthority()).isEqualTo("ROLE_USER");
    }

    @Test
    void getAuthorities_doesNotMutateRole() {
        User user = user(1L, "user", false);
        user.getAuthorities();
        assertThat(user.getRole()).isEqualTo("user");
    }

    @Test
    void getUsername_returnsEmail() {
        User user = user(1L, "USER", false);
        assertThat(user.getUsername()).isEqualTo("john@example.com");
    }
}
