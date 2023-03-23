package com.happytech.electronic.store.exception;

import com.happytech.electronic.store.dtos.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> userNotFoundExceptionHandler(UserNotFoundException u) {

        log.info("Exception Handler invoked..!!");

        ApiResponse apiResponse = ApiResponse.builder()
                .message(u.getMessage()).success(false).status(HttpStatus.NOT_FOUND).build();

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<String> emailNotFoundExceptionHandler(EmailNotFoundException e) {

        String message = e.getMessage();

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    //MethodArgumentNotValidException

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException ex) {

        Map<String, String> resp = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String defaultMessage = error.getDefaultMessage();
            resp.put(fieldName, defaultMessage);

        });

        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);


    }

    //handle Bad api Exception
    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponse> badApiRequestHandeler(BadApiRequest b) {

        log.info("Bad Api Request..!!");
        ApiResponse apiResponse = ApiResponse.builder()
                .message(b.getMessage()).success(false).status(HttpStatus.NOT_FOUND).build();

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

    }
}