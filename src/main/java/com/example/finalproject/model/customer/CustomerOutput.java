package com.example.finalproject.model.customer;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
public class CustomerOutput {

  private Long id;

  private String handle;

  private String email;

  @JsonIgnore
  private String password;

  private String firstName;

  private String lastName;

  @JsonIgnore
  private String role;
}
