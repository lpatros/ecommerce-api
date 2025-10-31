package com.lpatros.ecommerce_api.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "The CPF of the user is required")
    private String cpf;

    @NotBlank(message = "The name of the user is required")
    private String name;

    @NotBlank(message = "The phone number of the user is required")
    private String phoneNumber;

    @NotBlank(message = "The email of the user is required")
    private String email;

    @NotBlank(message = "The password of the user is required")
    private String password;

    private String confirmPassword;

    @NotNull(message = "The birth date of the user is required")
    private LocalDate birthDate;

    private String address;
}
