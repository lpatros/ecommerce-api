package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.phoneNumber.PhoneNumberRequest;
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

        Specification<User> specification = UserSpecification.filter(userFilter);

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
        validateCPFUniqueness(userRequest.getCpf());
        validateEmailUniqueness(userRequest.getEmail(), null);
        validatePhoneNumberUniqueness(userRequest.getPhoneNumbers(), null);

        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            throw new FieldsNotMatchException("password", "confirm password");
        }

        return userMapper.toResponse(userRepository.save(user));
    }

    public UserResponse update(Long id, UserRequest userRequest) {

        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("User", "id");
        }
        validateEmailUniqueness(userRequest.getEmail(), id);
        validatePhoneNumberUniqueness(userRequest.getPhoneNumbers(), id);

        User updatedUser = userMapper.toEntity(userRequest);
        updatedUser.setId(id);
        updatedUser.setCreatedAt(user.get().getCreatedAt());

        //TODO: Ajustar para manter telefones e endereços existentes

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

        userRepository.deleteById(id);
    }

    private void validateCPFUniqueness(String cpf) {
        boolean exists = userRepository.existsByCpf(cpf);
        if (exists) {
            throw new NotUniqueException("User", "CPF");
        }
    }

    private void validateEmailUniqueness(String email, Long userIdToIgnore) {
        if (userIdToIgnore == null) {
            boolean exists = userRepository.existsByEmail(email);
            if (exists) {
                throw new NotUniqueException("User", "email");
            }
        }

        if (userIdToIgnore != null) {
            boolean exists = userRepository.existsByEmailAndIdNot(email, userIdToIgnore);
            if (exists) {
                throw new NotUniqueException("User", "email");
            }
        }
    }

    private void validatePhoneNumberUniqueness(List<PhoneNumberRequest> phoneNumberRequests, Long userIdToIgnore) {
        for (PhoneNumberRequest phoneNumberRequest : phoneNumberRequests) {

            if (userIdToIgnore == null) {
                boolean exists = userRepository.existsByPhoneNumber_CountryCodeAndPhoneNumber_AreaCodeAndPhoneNumber_Number(
                        phoneNumberRequest.getCountryCode(),
                        phoneNumberRequest.getAreaCode(),
                        phoneNumberRequest.getNumber()
                );
                if (exists) {
                    throw new NotUniqueException("User", "phone number");
                }
            }

            if (userIdToIgnore != null) {
                boolean exists = userRepository.existsByPhoneNumber_CountryCodeAndPhoneNumber_AreaCodeAndPhoneNumber_NumberAndIdNot(
                        phoneNumberRequest.getCountryCode(),
                        phoneNumberRequest.getAreaCode(),
                        phoneNumberRequest.getNumber(),
                        userIdToIgnore
                );
                if (exists) {
                    throw new NotUniqueException("User", "phone number");
                }
            }
        }
    }
}
