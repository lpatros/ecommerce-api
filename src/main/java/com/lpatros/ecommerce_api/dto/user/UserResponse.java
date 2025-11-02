package com.lpatros.ecommerce_api.dto.user;

import com.lpatros.ecommerce_api.dto.order.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDate birthDate;
    private String address;
    private List<OrderResponse> orders;
    private LocalDateTime createdAt;
}
