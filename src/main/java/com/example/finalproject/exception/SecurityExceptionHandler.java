package com.example.finalproject.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
@Slf4j
public class SecurityExceptionHandler {

    //    FROM JWT AUTHENTICATION FILTER
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<Object> handleAuthenticationException(InternalAuthenticationServiceException exception) {
        log.warn("in internal authentication service exception");
        ApiError error = new ApiError(exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    //    FROM @PREAUTHORIZE in controller
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception) {
        ApiError error = new ApiError(exception.getMessage());
        return new ResponseEntity<>(error,HttpStatus.FORBIDDEN);
    }


}
