package com.lpatros.ecommerce_api.exception;

import lombok.Getter;

@Getter
public class NotNegativeException extends RuntimeException {
    private final String name;
    private final String field;

    public NotNegativeException(String name, String field) {
        super(String.format("%s cannot be negative for the field %s.", name, field));
        this.name = name;
        this.field = field;
    }
}