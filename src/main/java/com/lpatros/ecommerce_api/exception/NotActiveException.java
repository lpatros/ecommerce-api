package com.lpatros.ecommerce_api.exception;

import lombok.Getter;

@Getter
public class NotActiveException extends RuntimeException {
    private final String name;

    public NotActiveException(String name) {
        super(String.format("%s ja foi deletado", name));
        this.name = name;
    }
}
