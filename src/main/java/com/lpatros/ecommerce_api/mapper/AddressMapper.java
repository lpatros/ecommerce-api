package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.dto.address.AddressRequest;
import com.lpatros.ecommerce_api.dto.address.AddressResponse;
import com.lpatros.ecommerce_api.entity.Address;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressMapper {

    public AddressResponse toResponse(Address address) {
        return new AddressResponse(
            address.getId(),
            address.getStreet(),
            address.getNumber(),
            address.getComplement(),
            address.getZipCode(),
            address.getNeighborhood(),
            address.getCity(),
            address.getState()
        );
    }

    public List<AddressResponse> toResponseList(List<Address> addresses) {
        return addresses.stream()
                .map(this::toResponse)
                .toList();
    }

    public Address toEntity(AddressRequest addressRequest) {

        Long id = addressRequest.getId() != null
            ? addressRequest.getId()
            : null;

        return new Address(
            id,
            addressRequest.getStreet(),
            addressRequest.getNumber(),
            addressRequest.getComplement(),
            addressRequest.getZipCode(),
            addressRequest.getNeighborhood(),
            addressRequest.getCity(),
            addressRequest.getState(),
            null,
            Boolean.FALSE
        );
    }

    public List<Address> toEntityList(List<AddressRequest> addressRequests) {
        return addressRequests.stream()
                .map(this::toEntity)
                .toList();
    }
}
