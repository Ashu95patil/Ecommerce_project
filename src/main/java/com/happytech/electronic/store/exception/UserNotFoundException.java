package com.happytech.electronic.store.exception;

import com.happytech.electronic.store.config.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserNotFoundException extends  RuntimeException {

    String resourceName;
    String fieldName;

    long fieldValue;


    public UserNotFoundException(String resourceName, String fieldName, Long fieldValue) {
        super(String.format(AppConstants.STRING_FORMAT,resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
