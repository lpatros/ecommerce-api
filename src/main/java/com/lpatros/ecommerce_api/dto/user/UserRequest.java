package com.lpatros.ecommerce_api.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;

    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @NotBlank(message = "O número de telefone é obrigatório")
    private String phoneNumber;

    @NotBlank(message = "O email é obrigatório")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    private String password;

    private String confirmPassword;

    @NotNull(message = "A data de nascimento é obrigatória")
    private Date birthDate;
}
