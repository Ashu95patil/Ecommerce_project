package com.happytech.electronic.store.exception;

import com.happytech.electronic.store.config.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor


public class CategoryNotFoundException extends RuntimeException {

    String resourceName;

    String fieldName;

    Long fieldValue;


    public CategoryNotFoundException(String message) {
        super(message);

    }


    public CategoryNotFoundException(String resourceName, String fieldName, Long fieldValue) {
        super(String.format(AppConstants.STRING_FORMAT, resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
