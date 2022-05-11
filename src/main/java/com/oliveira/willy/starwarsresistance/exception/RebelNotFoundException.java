package com.oliveira.willy.starwarsresistance.exception;


public class RebelNotFoundException extends RuntimeException {
    public RebelNotFoundException(String message) {
        super(message);
    }
}