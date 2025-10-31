package com.lpatros.ecommerce_api.mapper;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.user.UserRequest;
import com.lpatros.ecommerce_api.dto.user.UserResponse;
import com.lpatros.ecommerce_api.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getCpf(),
                user.getName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getBirthDate(),
                user.getAddress(),
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
                userRequest.getPhoneNumber(),
                userRequest.getEmail(),
                userRequest.getPassword(),
                userRequest.getBirthDate(),
                userRequest.getAddress(),
                null,
                Boolean.FALSE
        );
    }
}
