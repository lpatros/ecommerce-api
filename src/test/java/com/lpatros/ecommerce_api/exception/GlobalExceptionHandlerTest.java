package com.lpatros.ecommerce_api.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        MessageSource messageSource = mock(MessageSource.class);
        handler = new GlobalExceptionHandler(messageSource);
    }

    @Test
    void handleNotFound_returns404() {
        ResponseEntity<RestErrorMessage> response =
                handler.handleNotFoundException(new NotFoundException("Product", "id"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        assert response.getBody() != null;
        assertThat(response.getBody().getStatus()).isEqualTo(404);
        assertThat(response.getBody().getMessage()).contains("Product");
    }

    @Test
    void handleBadRequestExceptions_returns400_forAllMappedTypes() {
        assertThat(handler.handleBadRequestExceptions(new NotActiveException("User")).getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(handler.handleBadRequestExceptions(new NotUniqueException("P", "name")).getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(handler.handleBadRequestExceptions(new NotMatchException()).getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(handler.handleBadRequestExceptions(new NotNegativeException("P", "stock")).getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(handler.handleBadRequestExceptions(new DuplicateItemsListException("Product")).getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleNoResourceFound_returns404() {
        ResponseEntity<RestErrorMessage> response =
                handler.handleNoResourceFoundException(
                        new org.springframework.web.servlet.resource.NoResourceFoundException(
                                org.springframework.http.HttpMethod.GET, "/nope", null));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        assert response.getBody() != null;
        assertThat(response.getBody().getMessage()).isEqualTo("No resource found");
    }

    @Test
    void handleDataIntegrityViolation_returns500() {
        ResponseEntity<RestErrorMessage> response =
                handler.handleDataIntegrityViolationException(new DataIntegrityViolationException("dup"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        assert response.getBody() != null;
        assertThat(response.getBody().getMessage()).isEqualTo("Violation of data integrity");
    }

    @Test
    void handleOptimisticLock_returns409() {
        ResponseEntity<RestErrorMessage> response =
                handler.handleObjectOptimisticLockingFailureException(
                        new ObjectOptimisticLockingFailureException("Order", 1L));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void handleBadCredentials_returns401() {
        ResponseEntity<RestErrorMessage> response =
                handler.handleBadCredentialsException(new BadCredentialsException("bad"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        assert response.getBody() != null;
        assertThat(response.getBody().getMessage()).isEqualTo("Invalid email or password");
    }

    @Test
    void handleAccessDenied_returns403() {
        ResponseEntity<RestErrorMessage> response =
                handler.handleAccessDeniedException(new AccessDeniedException("denied"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        assert response.getBody() != null;
        assertThat(response.getBody().getMessage()).isEqualTo("Access denied");
    }

    @Test
    void handleGenericException_returns500() {
        ResponseEntity<RestErrorMessage> response =
                handler.handleGenericException(new RuntimeException("boom"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        assert response.getBody() != null;
        assertThat(response.getBody().getMessage()).isEqualTo("An unexpected error occurred");
    }

    @Test
    void handleHttpMessageNotReadable_returns400() {
        ResponseEntity<RestErrorMessage> response =
                handler.handleHttpMessageNotReadableException(
                        new org.springframework.http.converter.HttpMessageNotReadableException(
                                "bad json", null));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        assert response.getBody() != null;
        assertThat(response.getBody().getMessage()).contains("invalid or malformed");
    }

    @Test
    void handleMethodArgumentTypeMismatch_returns400_withFormattedMessage() {
        ResponseEntity<RestErrorMessage> response =
                handler.handleMethodArgumentTypeMismatchException(
                        new org.springframework.web.method.annotation.MethodArgumentTypeMismatchException(
                                "abc", Long.class, "id", null, null));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        assert response.getBody() != null;
        assertThat(response.getBody().getMessage()).contains("id");
        assertThat(response.getBody().getMessage()).contains("abc");
    }
}
