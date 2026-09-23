package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserCpfFieldTest {

    @Test
    void cpfField_storesValue() {
        User user = new User(
                1L, "12345678900", "name", "phone", "e@x.com",
                "pwd", LocalDate.of(2000, 1, 1), null, List.of(),
                null, false, "USER"
        );
        assertThat(user.getCpf()).isEqualTo("12345678900");
        user.setCpf("99988877766");
        assertThat(user.getCpf()).isEqualTo("99988877766");
    }
}
