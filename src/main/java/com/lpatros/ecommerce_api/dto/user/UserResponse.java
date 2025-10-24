package com.lpatros.ecommerce_api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String cpf;
    private String name;
    private String phoneNumber;
    private String email;
    private Date birthDate;
    private LocalDateTime createdAt;
}
