package com.macedo.Customer.Exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String object) {
        super("%s not found".formatted(object));
    }
}
