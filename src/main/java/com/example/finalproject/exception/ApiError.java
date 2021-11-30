package com.example.finalproject.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ApiError {
  private final String message;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private final ZonedDateTime timeStamp;
  private Map<String, String> validationErrors;

  public ApiError(String message) {
    this.message = message;
    ZoneId zoneId = ZoneId.systemDefault();
    this.timeStamp = ZonedDateTime.now(zoneId);
  }
}
