package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserNameFieldTest {

    @Test
    void nameField_storesValue() {
        User user = new User(
                1L, "cpf", "Jane Doe", "phone", "e@x.com",
                "pwd", LocalDate.of(2000, 1, 1), null, List.of(),
                null, false, "USER"
        );
        assertThat(user.getName()).isEqualTo("Jane Doe");
        user.setName("J. Doe");
        assertThat(user.getName()).isEqualTo("J. Doe");
    }
}
