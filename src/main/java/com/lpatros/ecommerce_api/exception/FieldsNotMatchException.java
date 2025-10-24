package com.lpatros.ecommerce_api.exception;

import lombok.Getter;

@Getter
public class FieldsNotMatchException extends RuntimeException {
    public FieldsNotMatchException(String field1, String field2) {
        super(String.format("%s do not match %s", field1, field2));
    }

    public FieldsNotMatchException() {
        super("Fields do not match");
    }
}
