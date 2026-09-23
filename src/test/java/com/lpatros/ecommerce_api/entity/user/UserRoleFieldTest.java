package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserRoleFieldTest {

    @Test
    void roleField_storesRawValue_withoutMutationFromAuthorities() {
        User user = new User(
                1L, "cpf", "name", "phone", "e@x.com",
                "pwd", LocalDate.of(2000, 1, 1), null, List.of(),
                null, false, "admin"
        );
        user.getAuthorities();
        assertThat(user.getRole()).isEqualTo("admin");
        user.setRole("ADMIN");
        assertThat(user.getRole()).isEqualTo("ADMIN");
    }
}
