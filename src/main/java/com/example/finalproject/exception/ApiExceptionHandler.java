package com.example.finalproject.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class ApiExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        logger.warn("In handleApiRequestException");
        HttpStatus status;
        if (e.getStatus() == null) {
            status = HttpStatus.BAD_GATEWAY;
        } else {
            status = e.getStatus();
        }


        ApiError exception = new ApiError(
                e.getMessage(),
                status
        );


        return new ResponseEntity<Object>(exception, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleInvalidInputException(MethodArgumentNotValidException e) {
        logger.warn("In handleInvalid input");

        Map<String, String> fieldErrors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String message = error.getDefaultMessage();
            fieldErrors.put(fieldName, message);
        });

        ApiError error = new ApiError(
                "Input validation error",
                HttpStatus.BAD_REQUEST
        );
        error.setValidationErrors(fieldErrors);
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }




    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleServerException(Exception e) {
        logger.warn("IN server error");
        e.printStackTrace();
        ApiError error = new ApiError(
                "Some error occurred in server",
                HttpStatus.BAD_GATEWAY
        );
        return new ResponseEntity<Object>(error, HttpStatus.BAD_GATEWAY);
    }


}
