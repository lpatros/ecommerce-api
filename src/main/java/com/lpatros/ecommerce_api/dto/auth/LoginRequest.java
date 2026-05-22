package com.lpatros.ecommerce_api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "The email is required")
    @Email(message = "The email format is invalid")
    private String email;

    @NotBlank(message = "The password is required")
    private String password;
}
