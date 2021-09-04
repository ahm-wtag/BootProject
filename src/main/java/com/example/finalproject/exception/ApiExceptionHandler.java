package com.example.finalproject.exception;


import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


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


        return new ResponseEntity<>(exception, status);
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
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(JpaObjectRetrievalFailureException.class)
    public ResponseEntity<Object> handleEntityNotFoundException() {
        logger.warn("In entity not found exception");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        logger.warn("In handleDataIntegrityViolationException");

        String message = e.getMessage();
        for (Throwable exc = e.getCause(); exc != null; exc = exc.getCause()) {
            if (exc.getClass() == PSQLException.class) {
                message = exc.getMessage();
                break;
            }
        }
        logger.info(message);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException exception) {
        logger.warn("In handleIllegalStateException");

        ApiError error = new ApiError(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException() {
        logger.warn("In handleHttpMessageNotReadableException");
        ApiError error = new ApiError(
                "Request body format not supported",
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException( HttpRequestMethodNotSupportedException e ) {
        logger.warn("In handleHttpRequestMethodNotSupportedException");
         ApiError error = new ApiError(
                 "Allowed methods: " + Objects.requireNonNull(e.getSupportedHttpMethods()),
                 HttpStatus.METHOD_NOT_ALLOWED
         );
         return new ResponseEntity<>(error,HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleServerException(Exception e) {
        logger.warn("IN server error");
        e.printStackTrace();
        ApiError error = new ApiError(
                "Some error occurred in server",
                HttpStatus.BAD_GATEWAY
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);
    }


}
