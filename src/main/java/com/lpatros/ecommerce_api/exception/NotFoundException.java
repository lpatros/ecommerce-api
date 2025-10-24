package com.lpatros.ecommerce_api.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String name;
    private final String field;

    public NotFoundException(String name, String field) {
        super(String.format("%s not found with the %s", name, field));
        this.name = name;
        this.field = field;
    }
}
