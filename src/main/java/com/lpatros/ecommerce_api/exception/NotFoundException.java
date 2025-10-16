package com.lpatros.ecommerce_api.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String name;
    private final String field;

    public NotFoundException(String name, String field) {
        super(String.format("%s nao encontrado com o %s informado", name, field));
        this.name = name;
        this.field = field;
    }
}
