package com.bernmpdev.javerpersistenceservice.exception;

public class CustomerAlreadyExistsException extends RuntimeException {

    public CustomerAlreadyExistsException() {
        super("Customer with CPF already exists");
    }
}
