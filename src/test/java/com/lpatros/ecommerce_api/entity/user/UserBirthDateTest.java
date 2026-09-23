package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserBirthDateTest {

    @Test
    void birthDate_storesLocalDate() {
        LocalDate birth = LocalDate.of(1985, 6, 15);
        User user = new User(
                1L, "cpf", "name", "phone", "e@x.com",
                "pwd", birth, null, List.of(),
                null, false, "USER"
        );
        assertThat(user.getBirthDate()).isEqualTo(birth);
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        assertThat(user.getBirthDate()).isEqualTo(LocalDate.of(1990, 1, 1));
    }
}
