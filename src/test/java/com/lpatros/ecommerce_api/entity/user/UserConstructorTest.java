package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserConstructorTest {

    @Test
    void allArgsConstructor_setsAllFields() {
        LocalDateTime createdAt = LocalDateTime.of(2024, 1, 1, 10, 0);
        User user = new User(
                1L, "cpf", "name", "phone", "e@x.com",
                "pwd", LocalDate.of(1990, 5, 5), "addr", List.of(),
                createdAt, true, "ADMIN"
        );
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getCpf()).isEqualTo("cpf");
        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getPhoneNumber()).isEqualTo("phone");
        assertThat(user.getEmail()).isEqualTo("e@x.com");
        assertThat(user.getPassword()).isEqualTo("pwd");
        assertThat(user.getBirthDate()).isEqualTo(LocalDate.of(1990, 5, 5));
        assertThat(user.getAddress()).isEqualTo("addr");
        assertThat(user.getOrders()).isEmpty();
        assertThat(user.getCreatedAt()).isEqualTo(createdAt);
        assertThat(user.getDeleted()).isTrue();
        assertThat(user.getRole()).isEqualTo("ADMIN");
    }
}
