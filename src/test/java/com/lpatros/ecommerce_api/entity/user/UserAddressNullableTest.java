package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserAddressNullableTest {

    @Test
    void addressMayBeNull_andIsSettable() {
        User user = new User(
                1L, "cpf", "name", "phone", "e@x.com",
                "pwd", LocalDate.of(2000, 1, 1), null, List.of(),
                null, false, "USER"
        );
        assertThat(user.getAddress()).isNull();
        user.setAddress("Somewhere");
        assertThat(user.getAddress()).isEqualTo("Somewhere");
    }
}
