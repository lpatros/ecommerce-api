package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.dto.user.UserFilter;
import com.lpatros.ecommerce_api.dto.user.UserRequest;
import com.lpatros.ecommerce_api.dto.user.UserResponse;
import com.lpatros.ecommerce_api.entity.User;
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

    public Page<UserResponse> findAll(UserFilter userFilter, Pageable pageable) {

        Specification<User> specification = UserSpecification.filter(userFilter);

        Page<User> users = userRepository.findAll(specification, pageable);

        return userMapper.toResponsePage(users);
    }

    public UserResponse findById(Long id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new NotFoundException("Usuario", "id");
        }

        return userMapper.toResponse(user.get());
    }

    public UserResponse create(UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);

        List<User> existingUsersWithCpf = userRepository.findUsersByCpf(userRequest.getCpf());

        if (!existingUsersWithCpf.isEmpty()) {
            throw new NotUniqueException("Usuario", "CPF");
        }

        List<User> existingUsersWithPhoneNumber = userRepository.findUsersByPhoneNumber(userRequest.getPhoneNumber());

        if (!existingUsersWithPhoneNumber.isEmpty()) {
            throw new NotUniqueException("Usuario", "telefone");
        }

        List<User> existingUsersWithEmail = userRepository.findUsersByEmail(userRequest.getEmail());

        if (!existingUsersWithEmail.isEmpty()) {
            throw new NotUniqueException("Usuario", "email");
        }

        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("A senha e a confirmação de senha não coincidem.");
        }

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }
}
