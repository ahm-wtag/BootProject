package com.example.finalproject.model.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CustomerPartialInput {

  @Size(min = 1, message = "Customer Handle is required")
  private String handle;

  @Email
  private String email;

  @Size(min = 8, message = "Password must be at least 8 characters long")
  private String password;

  @Size(min = 1, message = "First name is required")
  private String firstName;

  private String lastName;
}
