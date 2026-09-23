package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserBirthDateNullableTest {

    @Test
    void birthDate_mayBeNullBeforeValidation() {
        User user = new User(
                1L, "cpf", "name", "phone", "e@x.com",
                "pwd", null, null, List.of(),
                null, false, "USER"
        );
        assertThat(user.getBirthDate()).isNull();
    }
}
