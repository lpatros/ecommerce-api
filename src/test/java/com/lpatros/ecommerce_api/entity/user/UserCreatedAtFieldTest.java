package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreatedAtFieldTest {

    @Test
    void createdAt_isNullableBeforePersist() {
        User user = new User(
                1L, "cpf", "name", "phone", "e@x.com",
                "pwd", LocalDate.of(2000, 1, 1), null, List.of(),
                null, false, "USER"
        );
        assertThat(user.getCreatedAt()).isNull();
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        assertThat(user.getCreatedAt()).isEqualTo(now);
    }
}
