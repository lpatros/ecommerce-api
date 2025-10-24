package com.lpatros.ecommerce_api.exception;

import lombok.Getter;

@Getter
public class NotUniqueException extends RuntimeException {
    private final String name;
    private final String field;

    public NotUniqueException(String name, String field) {
        super(String.format("%s already exists with the given %s.", name, field));
        this.name = name;
        this.field = field;
    }
}
