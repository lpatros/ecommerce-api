package com.lpatros.ecommerce_api.dto.user;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPatch {

    private String cpf;

    private String name;

    private String phoneNumber;

    @Email(message = "The email format is invalid")
    private String email;

    private String password;

    private String confirmPassword;

    private LocalDate birthDate;

    private String address;
}
