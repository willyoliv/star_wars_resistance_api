package com.oliveira.willy.starwarsresistence.exception;

public class DuplicateItemsInventoryException extends RuntimeException {
    public DuplicateItemsInventoryException(String message) {
        super(message);
    }
}
