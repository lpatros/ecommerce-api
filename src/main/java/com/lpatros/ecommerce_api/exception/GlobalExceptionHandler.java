package com.lpatros.ecommerce_api.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validacao",
                ex.getBindingResult().getFieldErrors().stream()
                        .collect(
                            Collectors.toMap(
                                    FieldError::getField,
                                    DefaultMessageSourceResolvable::getDefaultMessage
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
                "Corpo da requisicao invalido",
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RestErrorMessage> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        String message = String.format("O %s recebeu um valor invalido: %s", ex.getName(), ex.getValue());

        RestErrorMessage restErrorMessage = new RestErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                message,
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }
}
