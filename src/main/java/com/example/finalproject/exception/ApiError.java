package com.example.finalproject.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private final String message;
    private final HttpStatus httpStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private final ZonedDateTime timeStamp;
    private Map<String, String> validationErrors;

    public ApiError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
        ZoneId zoneId = ZoneId.systemDefault();
        this.timeStamp = ZonedDateTime.now(zoneId);
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
