package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.user.UserFilter;
import com.lpatros.ecommerce_api.dto.user.UserRequest;
import com.lpatros.ecommerce_api.dto.user.UserResponse;
import com.lpatros.ecommerce_api.entity.User;
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.mapper.UserMapper;
import com.lpatros.ecommerce_api.repository.UserRepository;
import com.lpatros.ecommerce_api.repository.specification.UserSpecification;
import com.lpatros.ecommerce_api.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserValidator userValidator, UserRepository userRepository, UserMapper userMapper) {
        this.userValidator = userValidator;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Pagination<UserResponse> findAll(UserFilter userFilter, Pageable pageable) {

        Specification<User> specification = UserSpecification.filter(userFilter);

        Page<User> users = userRepository.findAll(specification, pageable);

        return userMapper.toResponsePagination(users);
    }

    public UserResponse findById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User", "id"));

        return userMapper.toResponse(user);
    }

    public UserResponse create(UserRequest userRequest) {

        userValidator.validateCreate(userRequest);

        User user = userMapper.toEntity(userRequest);

        return userMapper.toResponse(userRepository.save(user));
    }

    public UserResponse update(Long id, UserRequest userRequest) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User", "id"));

        userValidator.validateUpdate(userRequest, id);

        User updatedUser = userMapper.toEntity(userRequest);
        updatedUser.setId(id);
        updatedUser.setCreatedAt(user.getCreatedAt());

        //TODO: Ajustar para manter telefones e endereços existentes

        return userMapper.toResponse(userRepository.save(updatedUser));
    }

    public void delete(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User", "id"));

        userRepository.deleteById(id);
    }
}
