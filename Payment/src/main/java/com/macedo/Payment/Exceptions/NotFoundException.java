package com.macedo.Payment.Exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String object) {
        super("%s not found".formatted(object));
    }
}
