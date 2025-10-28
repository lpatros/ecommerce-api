package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.dto.phoneNumber.PhoneNumberRequest;
import com.lpatros.ecommerce_api.dto.phoneNumber.PhoneNumberResponse;
import com.lpatros.ecommerce_api.entity.PhoneNumber;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PhoneNumberMapper {

    public PhoneNumberResponse toResponse(PhoneNumber phoneNumber) {
        return new PhoneNumberResponse(
            phoneNumber.getId(),
            phoneNumber.getCountryCode(),
            phoneNumber.getAreaCode(),
            phoneNumber.getNumber()
        );
    }

    public List<PhoneNumberResponse> toResponseList(List<PhoneNumber> phoneNumbers) {
        return phoneNumbers.stream()
                .map(this::toResponse)
                .toList();
    }

    public PhoneNumber toEntity(PhoneNumberRequest phoneNumberRequest) {

        Long id = phoneNumberRequest.getId() != null
            ? phoneNumberRequest.getId()
            : null;

        return new PhoneNumber(
            id,
            phoneNumberRequest.getCountryCode(),
            phoneNumberRequest.getAreaCode(),
            phoneNumberRequest.getNumber(),
            null,
            Boolean.FALSE
        );
    }

    public List<PhoneNumber> toEntityList(List<PhoneNumberRequest> phoneNumberRequests) {
        return phoneNumberRequests.stream()
                .map(this::toEntity)
                .toList();
    }
}
