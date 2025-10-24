package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.dto.address.AddressResponse;
import com.lpatros.ecommerce_api.dto.productImage.ProductImageResponse;
import com.lpatros.ecommerce_api.dto.user.UserRequest;
import com.lpatros.ecommerce_api.dto.user.UserResponse;
import com.lpatros.ecommerce_api.entity.Address;
import com.lpatros.ecommerce_api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    private final AddressMapper addressMapper;

    @Autowired
    public UserMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public UserResponse toResponse(User user) {

        List<AddressResponse> addressResponses =
                (user.getAddresses() != null && !user.getAddresses().isEmpty())
                        ? addressMapper.toResponseList(user.getAddresses())
                        : List.of();

        return new UserResponse(
                user.getId(),
                user.getCpf(),
                user.getName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getBirthDate(),
                addressResponses,
                user.getCreatedAt()
        );
    }

    public Page<UserResponse> toResponsePage(Page<User> users) {
        return users.map(this::toResponse);
    }

    public User toEntity(UserRequest userRequest) {

        User user = new User(
                null,
                userRequest.getCpf(),
                userRequest.getName(),
                userRequest.getPhoneNumber(),
                userRequest.getEmail(),
                userRequest.getPassword(),
                userRequest.getBirthDate(),
                null,
                null,
                Boolean.FALSE
        );

        List<Address> addresses = addressMapper.toEntityList(userRequest.getAddresses());

        addresses.forEach(address -> address.setUser(user));
        user.setAddresses(addresses);

        return user;
    }
}
