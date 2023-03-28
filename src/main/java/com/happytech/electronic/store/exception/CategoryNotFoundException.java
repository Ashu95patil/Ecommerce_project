package com.happytech.electronic.store.exception;

public class CategoryNotFoundException extends RuntimeException{

    String resourceName;

    String fieldName;

    Long fieldValue;



    public CategoryNotFoundException(String message) {
        super(message);

    }


    public CategoryNotFoundException(String resourceName, String fieldName,Long fieldValue) {
        super(String.format("%s not found with %s : %s",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
