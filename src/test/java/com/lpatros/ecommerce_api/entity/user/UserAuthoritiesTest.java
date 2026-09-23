package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserAuthoritiesTest {

    @Test
    void userImplementsUserDetailsContract() {
        User user = new User(
                1L, "cpf", "name", "phone", "email@x.com",
                "pwd", LocalDate.of(2000, 1, 1), null, List.of(),
                null, false, "ADMIN"
        );
        assertThat(org.springframework.security.core.userdetails.UserDetails.class)
                .isAssignableFrom(user.getClass());
        assertThat(user.getPassword()).isEqualTo("pwd");
        assertThat(user.isAccountNonExpired()).isTrue();
        assertThat(user.isAccountNonLocked()).isTrue();
        assertThat(user.isCredentialsNonExpired()).isTrue();
    }
}
