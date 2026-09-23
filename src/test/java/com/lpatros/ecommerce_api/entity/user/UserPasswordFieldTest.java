package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserPasswordFieldTest {

    @Test
    void passwordField_storesEncodedValue() {
        User user = new User(
                1L, "cpf", "name", "phone", "e@x.com",
                "$2a$10$encodedhash", LocalDate.of(2000, 1, 1), null, List.of(),
                null, false, "USER"
        );
        assertThat(user.getPassword()).isEqualTo("$2a$10$encodedhash");
        user.setPassword("$2a$10$newhash");
        assertThat(user.getPassword()).isEqualTo("$2a$10$newhash");
    }
}
