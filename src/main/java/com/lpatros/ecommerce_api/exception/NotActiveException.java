package com.lpatros.ecommerce_api.exception;

import lombok.Getter;

@Getter
public class NotActiveException extends RuntimeException {
    private final String name;

    public NotActiveException(String name) {
        super(String.format("%s já está inativo", name));
        this.name = name;
    }
}
