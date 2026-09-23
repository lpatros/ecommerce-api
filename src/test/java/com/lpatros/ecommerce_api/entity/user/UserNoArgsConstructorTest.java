package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserNoArgsConstructorTest {

    @Test
    void noArgsConstructor_producesEmptyInstance() {
        User user = new User();
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isNull();
        assertThat(user.getRole()).isNull();
        assertThat(user.getDeleted()).isNull();
    }
}
