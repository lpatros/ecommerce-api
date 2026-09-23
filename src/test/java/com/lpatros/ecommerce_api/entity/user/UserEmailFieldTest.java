package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserEmailFieldTest {

    @Test
    void emailField_storesValue() {
        User user = new User(
                1L, "cpf", "name", "phone", "old@mail.com",
                "pwd", LocalDate.of(2000, 1, 1), null, List.of(),
                null, false, "USER"
        );
        assertThat(user.getEmail()).isEqualTo("old@mail.com");
        user.setEmail("new@mail.com");
        assertThat(user.getEmail()).isEqualTo("new@mail.com");
    }
}
