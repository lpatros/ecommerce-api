package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserEntityAnnotationTest {

    @Test
    void userIsJpaEntity() {
        assertThat(User.class.getAnnotation(Entity.class)).isNotNull();
    }
}
