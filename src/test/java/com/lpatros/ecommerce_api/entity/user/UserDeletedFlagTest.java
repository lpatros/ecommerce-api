package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserDeletedFlagTest {

    @Test
    void softDeleteFlag_controlsIsEnabled() {
        User active = new User(
                1L, "cpf", "name", "phone", "e@x.com",
                "pwd", LocalDate.of(2000, 1, 1), null, List.of(),
                null, false, "USER"
        );
        User deleted = new User(
                2L, "cpf2", "name", "phone", "e2@x.com",
                "pwd", LocalDate.of(2000, 1, 1), null, List.of(),
                null, true, "USER"
        );
        assertThat(active.isEnabled()).isTrue();
        assertThat(deleted.isEnabled()).isFalse();
    }
}
