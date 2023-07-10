package com.general.store.exception;

public class CurrentUserNotFoundException extends RuntimeException{
    public CurrentUserNotFoundException(String message) {
        super(message);
    }
}
