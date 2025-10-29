package com.lpatros.ecommerce_api.dto.address;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {

    private Long id;

    @NotBlank(message = "The street field is required.")
    private String street;

    @NotBlank(message = "The number field is required.")
    private String number;

    private String complement;

    @NotBlank(message = "The zipCode field is required.")
    private String zipCode;

    @NotBlank(message = "The neighborhood field is required.")
    private String neighborhood;

    @NotBlank(message = "The city field is required.")
    private String city;

    @NotBlank(message = "The state field is required.")
    private String state;
}
