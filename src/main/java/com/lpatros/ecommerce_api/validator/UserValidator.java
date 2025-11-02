package com.lpatros.ecommerce_api.validator;

import com.lpatros.ecommerce_api.dto.user.UserPatch;
import com.lpatros.ecommerce_api.dto.user.UserRequest;
import com.lpatros.ecommerce_api.exception.NotMatchException;
import com.lpatros.ecommerce_api.exception.NotUniqueException;
import com.lpatros.ecommerce_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    private final UserRepository userRepository;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateCreate(UserRequest userRequest) {
        validateCPFUnique(userRequest.getCpf());
        validateEmailUnique(userRequest.getEmail(), null);
        validatePhoneNumberUnique(userRequest.getPhoneNumber(), null);
        validatePasswordCombination(userRequest.getPassword(), userRequest.getConfirmPassword());
    }

    public void validateUpdate(UserRequest userRequest, Long userId) {
        validateEmailUnique(userRequest.getEmail(), userId);
        validatePhoneNumberUnique(userRequest.getPhoneNumber(), userId);
    }

    public void validatePatch(UserPatch userPatch, Long userId) {
        if (userPatch.getEmail() != null) {
            validateEmailUnique(userPatch.getEmail(), userId);
        }
        if (userPatch.getPhoneNumber() != null) {
            validatePhoneNumberUnique(userPatch.getPhoneNumber(), userId);
        }
    }

    private void validateCPFUnique(String cpf) {
        boolean exists = userRepository.existsByCpf(cpf);
        if (exists) {
            throw new NotUniqueException("User", "CPF");
        }
    }

    private void validateEmailUnique(String email, Long idToIgnore) {
        boolean exists;
        if (idToIgnore == null) {
            exists = userRepository.existsByEmail(email);
        } else {
            exists = userRepository.existsByEmailAndIdNot(email, idToIgnore);
        }
        if (exists) {
            throw new NotUniqueException("User", "Email");
        }
    }

    private void validatePhoneNumberUnique(String phoneNumber, Long userIdToIgnore) {
        boolean exists;
        if (userIdToIgnore == null) {
            exists = userRepository.existsByPhoneNumber(phoneNumber);
        } else {
            exists = userRepository.existsByPhoneNumberAndIdNot(phoneNumber, userIdToIgnore);
        }
        if (exists) {
            throw new NotUniqueException("User", "Phone Number");
        }
    }

    private void validatePasswordCombination(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new NotMatchException("password", "confirm password");
        }
    }
}
