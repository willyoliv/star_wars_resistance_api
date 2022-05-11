package com.oliveira.willy.starwarsresistance.exception;

public class DuplicateItemsInventoryException extends RuntimeException {
    public DuplicateItemsInventoryException(String message) {
        super(message);
    }
}
