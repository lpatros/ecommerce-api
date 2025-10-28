package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.address.AddressResponse;
import com.lpatros.ecommerce_api.dto.phoneNumber.PhoneNumberResponse;
import com.lpatros.ecommerce_api.dto.user.UserRequest;
import com.lpatros.ecommerce_api.dto.user.UserResponse;
import com.lpatros.ecommerce_api.entity.Address;
import com.lpatros.ecommerce_api.entity.PhoneNumber;
import com.lpatros.ecommerce_api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    private final PhoneNumberMapper phoneNumberMapper;
    private final AddressMapper addressMapper;

    @Autowired
    public UserMapper(PhoneNumberMapper phoneNumberMapper, AddressMapper addressMapper) {
        this.phoneNumberMapper = phoneNumberMapper;
        this.addressMapper = addressMapper;
    }

    public UserResponse toResponse(User user) {

        List<PhoneNumberResponse> phoneNumberResponses =
                (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty())
                        ? phoneNumberMapper.toResponseList(user.getPhoneNumber())
                        : List.of();

        List<AddressResponse> addressResponses =
                (user.getAddresses() != null && !user.getAddresses().isEmpty())
                        ? addressMapper.toResponseList(user.getAddresses())
                        : List.of();

        return new UserResponse(
                user.getId(),
                user.getCpf(),
                user.getName(),
                phoneNumberResponses,
                user.getEmail(),
                user.getBirthDate(),
                addressResponses,
                user.getCreatedAt()
        );
    }

    public Pagination<UserResponse> toResponsePagination(Page<User> users) {
        return Pagination.toPagination(users.map(this::toResponse));
    }

    public User toEntity(UserRequest userRequest) {

        User user = new User(
                null,
                userRequest.getCpf(),
                userRequest.getName(),
                null,
                userRequest.getEmail(),
                userRequest.getPassword(),
                userRequest.getBirthDate(),
                null,
                null,
                Boolean.FALSE
        );

        List<PhoneNumber> phoneNumbers = phoneNumberMapper.toEntityList(userRequest.getPhoneNumbers());
        phoneNumbers.forEach(phoneNumber -> phoneNumber.setUser(user));
        user.setPhoneNumber(phoneNumbers);

        List<Address> addresses = addressMapper.toEntityList(userRequest.getAddresses());
        addresses.forEach(address -> address.setUser(user));
        user.setAddresses(addresses);

        return user;
    }
}
