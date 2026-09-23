package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;

class UserImplementsUserDetailsTest {

    @Test
    void userIsUserDetails() {
        assertThat(UserDetails.class.isAssignableFrom(User.class)).isTrue();
    }
}
