package com.example.finalproject.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
@Order(2)
public class ApiExceptionHandler {


    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        log.warn("In handleApiRequestException");

        ApiError exception = new ApiError(
                e.getMessage()
        );

        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

//    For validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleInvalidInputException(MethodArgumentNotValidException e) {
        log.warn("In handleInvalid input");

        Map<String, String> fieldErrors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String message = error.getDefaultMessage();
            fieldErrors.put(fieldName, message);
        });

        ApiError error = new ApiError("Input validation error");
        error.setValidationErrors(fieldErrors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

//  For malformed Url's
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(JpaObjectRetrievalFailureException.class)
    public ResponseEntity<Object> handleEntityNotFoundException() {
        log.warn("In entity not found exception");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException e) {

        return Optional.ofNullable(e.getMessage())
                .map( errorMsg -> {
                    String message = e.getMessage().contains("customer_handle") ? "Username Already Exists": "Email already in use";
                    ApiError error = new ApiError(message);
                    return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);

                }).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));

    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException exception) {
        log.warn("In handleIllegalStateException");

        ApiError error = new ApiError(exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException() {
        log.warn("In handleHttpMessageNotReadableException");
        ApiError error = new ApiError("Request body format not supported");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn("In handleHttpRequestMethodNotSupportedException");
        ApiError error = new ApiError("Allowed methods: " + Objects.requireNonNull(e.getSupportedHttpMethods()));
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleNoResultException() {
        log.warn("In handleHttpRequestMethodNotSupportedException");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleServerException(Exception e) {
        log.warn("IN server error");
        e.printStackTrace();
        ApiError error = new ApiError("Some error occurred in server");
        return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);
    }



}

