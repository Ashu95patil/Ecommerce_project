package com.happytech.electronic.store.exception;

import com.happytech.electronic.store.config.AppConstants;

public class BadApiRequestException extends RuntimeException{

    public BadApiRequestException(String message) {
        super(message);
    }
    public BadApiRequestException() {
        super(AppConstants.BAD_REQUEST);
    }

}
