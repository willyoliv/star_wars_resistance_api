package com.oliveira.willy.starwarsresistence.exception;


public class RebelNotFoundException extends RuntimeException {
    public RebelNotFoundException(String message) {
        super(message);
    }
}