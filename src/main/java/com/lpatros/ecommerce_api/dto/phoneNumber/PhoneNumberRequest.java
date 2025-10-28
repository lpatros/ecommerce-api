package com.lpatros.ecommerce_api.dto.phoneNumber;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberRequest {

    private Long id;

    @NotBlank(message = "Country code is required")
    private String countryCode;

    @NotBlank(message = "Area code is required")
    private String areaCode;

    @NotBlank(message = "Phone number is required")
    private String number;
}
