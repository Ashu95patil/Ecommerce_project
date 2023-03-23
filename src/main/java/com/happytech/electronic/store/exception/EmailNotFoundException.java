package com.happytech.electronic.store.exception;

public class EmailNotFoundException extends  RuntimeException{

    public EmailNotFoundException(String message) {
        super(message);
    }
}
