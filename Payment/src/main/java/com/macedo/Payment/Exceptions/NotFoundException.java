package com.macedo.Payment.Exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String object) {
        super(String.format("%s not found",object));
    }
}
