package com.lpatros.ecommerce_api.validator;

import com.lpatros.ecommerce_api.dto.address.AddressRequest;
import com.lpatros.ecommerce_api.dto.phoneNumber.PhoneNumberRequest;
import com.lpatros.ecommerce_api.dto.user.UserRequest;
import com.lpatros.ecommerce_api.exception.DuplicateItemsListException;
import com.lpatros.ecommerce_api.exception.FieldsNotMatchException;
import com.lpatros.ecommerce_api.exception.NotUniqueException;
import com.lpatros.ecommerce_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        validatePhoneNumberUnique(userRequest.getPhoneNumbers(), null);
        validatePasswordCombination(userRequest.getPassword(), userRequest.getConfirmPassword());
        validateDuplicateItemsInList(userRequest.getAddresses(), "Address");
        validateDuplicateItemsInList(userRequest.getPhoneNumbers(), "Phone number");
    }

    public void validateUpdate(UserRequest userRequest, Long userId) {
        validateEmailUnique(userRequest.getEmail(), userId);
        validatePhoneNumberUnique(userRequest.getPhoneNumbers(), userId);
        validateDuplicateItemsInList(userRequest.getAddresses(), "Address");
        validateDuplicateItemsInList(userRequest.getPhoneNumbers(), "Phone number");
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

    private void validatePhoneNumberUnique(List<PhoneNumberRequest> phoneNumberRequests, Long userIdToIgnore) {
        for (PhoneNumberRequest phoneNumberRequest : phoneNumberRequests) {
            boolean exists;
            if (userIdToIgnore == null) {
                exists = userRepository.existsByPhoneNumber_CountryCodeAndPhoneNumber_AreaCodeAndPhoneNumber_Number(
                        phoneNumberRequest.getCountryCode(),
                        phoneNumberRequest.getAreaCode(),
                        phoneNumberRequest.getNumber()
                );
                if (exists) {
                    throw new NotUniqueException("User", "phone number");
                }
            } else {
                exists = userRepository.existsByPhoneNumber_CountryCodeAndPhoneNumber_AreaCodeAndPhoneNumber_NumberAndIdNot(
                        phoneNumberRequest.getCountryCode(),
                        phoneNumberRequest.getAreaCode(),
                        phoneNumberRequest.getNumber(),
                        userIdToIgnore
                );
            }
            if (exists) {
                throw new NotUniqueException("User", "phone number");
            }
        }
    }

    private void validatePasswordCombination(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new FieldsNotMatchException("password", "confirm password");
        }
    }

    private <T> void validateDuplicateItemsInList(List<T> items, String itemType) {
        Set<T> itemSet = new HashSet<>();
        for (T item : items) {
            if (!itemSet.add(item)) {
                throw new DuplicateItemsListException(itemType);
            }
        }
    }
}
