package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserSoftDeleteAnnotationTest {

    @Test
    void userHasSoftDeleteAnnotations() {
        SQLDelete sqlDelete = User.class.getAnnotation(SQLDelete.class);
        SQLRestriction sqlRestriction = User.class.getAnnotation(SQLRestriction.class);
        assertThat(sqlDelete).isNotNull();
        assertThat(sqlDelete.sql()).contains("UPDATE users SET deleted = true");
        assertThat(sqlRestriction).isNotNull();
        assertThat(sqlRestriction.value()).isEqualTo("deleted = false");
    }
}
