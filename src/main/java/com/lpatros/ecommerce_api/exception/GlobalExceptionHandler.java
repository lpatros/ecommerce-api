package com.lpatros.ecommerce_api.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<RestErrorMessage> NotFoundException(NotFoundException ex) {;
        RestErrorMessage restErrorMessage = new RestErrorMessage(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restErrorMessage);
    }

    @ExceptionHandler(NotActiveException.class)
    private ResponseEntity<RestErrorMessage> NotActiveException(NotActiveException ex) {;
        RestErrorMessage restErrorMessage = new RestErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }

    @ExceptionHandler(NotUniqueException.class)
    private ResponseEntity<RestErrorMessage> NotUniqueException(NotUniqueException ex) {;
        RestErrorMessage restErrorMessage = new RestErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }

    @ExceptionHandler(NotMatchException.class)
    private ResponseEntity<RestErrorMessage> FieldsNotMatchException(NotMatchException ex) {;
        RestErrorMessage restErrorMessage = new RestErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }

    @ExceptionHandler(NotNegativeException.class)
    private ResponseEntity<RestErrorMessage> NotNegativeException(NotNegativeException ex) {;
        RestErrorMessage restErrorMessage = new RestErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }

    @ExceptionHandler(DuplicateItemsListException.class)
    private ResponseEntity<RestErrorMessage> DuplicateItemsListException(DuplicateItemsListException ex) {;
        RestErrorMessage restErrorMessage = new RestErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed for fields",
                ex.getBindingResult().getFieldErrors().stream()
                        .collect(
                            Collectors.toMap(
                                    FieldError::getField,
                                    fieldError -> messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())
                            )
                        )
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Body of the request is invalid or malformed",
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RestErrorMessage> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        String message = String.format("The parameter '%s' with value '%s' could not be converted to type '%s'",
                ex.getName(),
                ex.getValue(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "Unknown");

        RestErrorMessage restErrorMessage = new RestErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                message,
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<RestErrorMessage> handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException ex) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Invalid data access resource usage",
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restErrorMessage);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<RestErrorMessage> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Violation of data integrity",
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restErrorMessage);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<RestErrorMessage> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Invalid data access API usage",
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restErrorMessage);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<RestErrorMessage> handleNoResourceFoundException(NoResourceFoundException ex) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "No resource found",
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restErrorMessage);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<RestErrorMessage> handleObjectOptimisticLockingFailureException(ObjectOptimisticLockingFailureException ex) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflict occurred due to concurrent modification",
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(restErrorMessage);
    }
}
