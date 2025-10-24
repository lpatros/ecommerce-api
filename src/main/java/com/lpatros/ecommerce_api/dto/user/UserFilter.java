package com.lpatros.ecommerce_api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFilter {
    private String cpf;
    private String name;
    private String phoneNumber;
    private String email;
}
