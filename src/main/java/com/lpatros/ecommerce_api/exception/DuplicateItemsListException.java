package com.lpatros.ecommerce_api.exception;

import lombok.Getter;

@Getter
public class DuplicateItemsListException extends RuntimeException {

    private final String itemType;

    public DuplicateItemsListException(String itemType) {
        super("Duplicate " + itemType + " found in the list.");
        this.itemType = itemType;
    }
}
