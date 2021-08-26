package com.example.finalproject.exception;

import org.springframework.http.HttpStatus;

public class ApiRequestException extends RuntimeException {

    private HttpStatus status;

    public ApiRequestException(String s) {
        super(s);
    }

    public ApiRequestException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ApiRequestException(Throwable throwable) {
        super(throwable);
    }

    public ApiRequestException(String s, HttpStatus status, Throwable throwable) {
        super(s, throwable);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
