package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.user.UserRequest;
import com.lpatros.ecommerce_api.dto.user.UserResponse;
import com.lpatros.ecommerce_api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

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
        return new UserResponse(
                user.getId(),
                user.getCpf(),
                user.getName(),
                phoneNumberMapper.toResponseList(user.getPhoneNumber()),
                user.getEmail(),
                user.getBirthDate(),
                addressMapper.toResponseList(user.getAddresses()),
                user.getCreatedAt()
        );
    }

    public Pagination<UserResponse> toResponsePagination(Page<User> users) {
        return Pagination.toPagination(users.map(this::toResponse));
    }

    public User toEntity(UserRequest userRequest) {
        return new User(
                null,
                userRequest.getCpf(),
                userRequest.getName(),
                phoneNumberMapper.toEntityList(userRequest.getPhoneNumbers()),
                userRequest.getEmail(),
                userRequest.getPassword(),
                userRequest.getBirthDate(),
                addressMapper.toEntityList(userRequest.getAddresses()),
                null,
                Boolean.FALSE
        );
    }
}
