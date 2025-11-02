package com.lpatros.ecommerce_api.exception;

import lombok.Getter;

@Getter
public class NotMatchException extends RuntimeException {
    public NotMatchException(String field1, String field2) {
        super(String.format("%s do not match %s", field1, field2));
    }

    public NotMatchException() {
        super("Fields do not match");
    }
}
