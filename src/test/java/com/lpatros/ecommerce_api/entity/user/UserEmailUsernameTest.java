package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserEmailUsernameTest {

    @Test
    void getUsername_isEmail() {
        User user = new User(
                1L, "cpf", "name", "phone", "unique@email.com",
                "pwd", LocalDate.of(2000, 1, 1), null, List.of(),
                null, false, "USER"
        );
        assertThat(user.getUsername()).isEqualTo("unique@email.com");
        assertThat(user.getEmail()).isEqualTo("unique@email.com");
    }
}
