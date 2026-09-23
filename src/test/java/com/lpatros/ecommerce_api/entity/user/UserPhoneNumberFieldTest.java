package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserPhoneNumberFieldTest {

    @Test
    void phoneNumber_storesValue() {
        User user = new User(
                1L, "cpf", "name", "11999999999", "e@x.com",
                "pwd", LocalDate.of(2000, 1, 1), null, List.of(),
                null, false, "USER"
        );
        assertThat(user.getPhoneNumber()).isEqualTo("11999999999");
        user.setPhoneNumber("11888888888");
        assertThat(user.getPhoneNumber()).isEqualTo("11888888888");
    }
}
