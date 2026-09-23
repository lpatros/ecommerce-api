package com.lpatros.ecommerce_api.validator;

import com.lpatros.ecommerce_api.dto.user.UserPatch;
import com.lpatros.ecommerce_api.dto.user.UserRequest;
import com.lpatros.ecommerce_api.exception.NotMatchException;
import com.lpatros.ecommerce_api.exception.NotUniqueException;
import com.lpatros.ecommerce_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserValidator userValidator;

    private UserRequest validRequest() {
        UserRequest r = new UserRequest();
        r.setCpf("12345678900");
        r.setName("John");
        r.setPhoneNumber("11999999999");
        r.setEmail("john@example.com");
        r.setPassword("secret");
        r.setConfirmPassword("secret");
        r.setBirthDate(LocalDate.of(1990, 1, 1));
        r.setAddress("Street 1");
        return r;
    }

    private void stubNoDuplicates() {
        lenient().when(userRepository.existsByCpf(anyString())).thenReturn(false);
        lenient().when(userRepository.existsByEmail(anyString())).thenReturn(false);
        lenient().when(userRepository.existsByPhoneNumber(anyString())).thenReturn(false);
    }

    @Test
    void validateCreate_throws_whenCpfExists() {
        when(userRepository.existsByCpf("12345678900")).thenReturn(true);
        assertThatThrownBy(() -> userValidator.validateCreate(validRequest()))
                .isInstanceOf(NotUniqueException.class)
                .hasMessageContaining("CPF");
    }

    @Test
    void validateCreate_throws_whenEmailExists() {
        when(userRepository.existsByCpf(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        assertThatThrownBy(() -> userValidator.validateCreate(validRequest()))
                .isInstanceOf(NotUniqueException.class)
                .hasMessageContaining("Email");
    }

    @Test
    void validateCreate_throws_whenPhoneExists() {
        when(userRepository.existsByCpf(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByPhoneNumber(anyString())).thenReturn(true);
        assertThatThrownBy(() -> userValidator.validateCreate(validRequest()))
                .isInstanceOf(NotUniqueException.class)
                .hasMessageContaining("Phone Number");
    }

    @Test
    void validateCreate_throws_whenPasswordsDontMatch() {
        stubNoDuplicates();
        UserRequest req = validRequest();
        req.setConfirmPassword("different");
        assertThatThrownBy(() -> userValidator.validateCreate(req))
                .isInstanceOf(NotMatchException.class);
    }

    @Test
    void validateCreate_passes_whenValid() {
        stubNoDuplicates();
        assertThatCode(() -> userValidator.validateCreate(validRequest()))
                .doesNotThrowAnyException();
    }

    @Test
    void validateUpdate_usesIgnoringId_whenUpdating() {
        when(userRepository.existsByEmailAndIdNot(anyString(), anyLong())).thenReturn(false);
        when(userRepository.existsByPhoneNumberAndIdNot(anyString(), anyLong())).thenReturn(false);
        assertThatCode(() -> userValidator.validateUpdate(validRequest(), 1L))
                .doesNotThrowAnyException();
    }

    @Test
    void validatePatch_allNull_passes() {
        assertThatCode(() -> userValidator.validatePatch(new UserPatch(), 1L))
                .doesNotThrowAnyException();
    }

    @Test
    void validatePatch_throws_whenEmailDuplicateOnUpdate() {
        UserPatch patch = new UserPatch();
        patch.setEmail("john@example.com");
        when(userRepository.existsByEmailAndIdNot(anyString(), anyLong())).thenReturn(true);
        assertThatThrownBy(() -> userValidator.validatePatch(patch, 1L))
                .isInstanceOf(NotUniqueException.class);
    }
}
