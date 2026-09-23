package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserDeletedBooleanTest {

    @Test
    void deletedField_setterWorks_andIsEnabledTracksIt() {
        User user = new User(
                1L, "cpf", "name", "phone", "e@x.com",
                "pwd", LocalDate.of(2000, 1, 1), null, List.of(),
                null, false, "USER"
        );
        assertThat(user.getDeleted()).isFalse();
        assertThat(user.isEnabled()).isTrue();

        user.setDeleted(true);
        assertThat(user.getDeleted()).isTrue();
        assertThat(user.isEnabled()).isFalse();
    }
}
