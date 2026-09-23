package com.lpatros.ecommerce_api.entity.user;

import com.lpatros.ecommerce_api.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserRoleAuthoritiesTest {

    private User build(String role) {
        return new User(
                1L, "cpf", "name", "phone", "e@x.com",
                "pwd", LocalDate.of(2000, 1, 1), null, List.of(),
                null, false, role
        );
    }

    @Test
    void adminRole_mapsToRoleAdmin() {
        var authorities = build("ADMIN").getAuthorities();
        assertThat(authorities).first()
                .isInstanceOf(SimpleGrantedAuthority.class)
                .extracting(a -> ((SimpleGrantedAuthority) a).getAuthority())
                .isEqualTo("ROLE_ADMIN");
    }

    @Test
    void mixedCaseRole_uppercasedInAuthority() {
        var authorities = build("seller").getAuthorities();
        assertThat(authorities).first()
                .extracting(a -> ((SimpleGrantedAuthority) a).getAuthority())
                .isEqualTo("ROLE_SELLER");
    }
}
