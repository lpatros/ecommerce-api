package com.lpatros.ecommerce_api.dto.phoneNumber;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberResponse {
    private Long id;
    private String countryCode;
    private String areaCode;
    private String number;
}
