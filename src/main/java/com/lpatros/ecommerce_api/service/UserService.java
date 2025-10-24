package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.user.UserFilter;
import com.lpatros.ecommerce_api.dto.user.UserRequest;
import com.lpatros.ecommerce_api.dto.user.UserResponse;
import com.lpatros.ecommerce_api.entity.User;
import com.lpatros.ecommerce_api.exception.FieldsNotMatchException;
import com.lpatros.ecommerce_api.exception.NotActiveException;
import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.exception.NotUniqueException;
import com.lpatros.ecommerce_api.mapper.UserMapper;
import com.lpatros.ecommerce_api.repository.UserRepository;
import com.lpatros.ecommerce_api.repository.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Pagination<UserResponse> findAll(UserFilter userFilter, Pageable pageable) {

        Specification<User> notDeleted = UserSpecification.isNotDeleted();
        Specification<User> specification = UserSpecification.filter(userFilter).and(notDeleted);

        Page<User> users = userRepository.findAll(specification, pageable);

        return userMapper.toResponsePagination(users);
    }

    public UserResponse findById(Long id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new NotFoundException("User", "id");
        }

        if (user.get().getDeleted()) {
            throw new NotActiveException("User");
        }

        return userMapper.toResponse(user.get());
    }

    public UserResponse create(UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);

        List<User> existingUsersWithCpf = userRepository.findUsersByCpf(userRequest.getCpf());

        if (!existingUsersWithCpf.isEmpty()) {
            throw new NotUniqueException("User", "CPF");
        }

        List<User> existingUsersWithPhoneNumber = userRepository.findUsersByPhoneNumber(userRequest.getPhoneNumber());

        if (!existingUsersWithPhoneNumber.isEmpty()) {
            throw new NotUniqueException("User", "phone number");
        }

        List<User> existingUsersWithEmail = userRepository.findUsersByEmail(userRequest.getEmail());

        if (!existingUsersWithEmail.isEmpty()) {
            throw new NotUniqueException("User", "email");
        }

        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            throw new FieldsNotMatchException("password", "confirm password");
        }

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    public UserResponse update(Long id, UserRequest userRequest) {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new NotFoundException("User", "id");
        }

        User updatedUser = userMapper.toEntity(userRequest);
        updatedUser.setId(id);
        updatedUser.setCreatedAt(user.get().getCreatedAt());

        return userMapper.toResponse(userRepository.save(updatedUser));
    }

    public void delete(Long id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new NotFoundException("User", "id");
        }

        if (user.get().getDeleted()) {
            throw new NotActiveException("User");
        }

        userRepository.disable(id);
    }
}
