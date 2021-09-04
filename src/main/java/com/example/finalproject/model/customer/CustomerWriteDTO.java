package com.example.finalproject.model.customer;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CustomerWriteDTO {


    @Size(min = 1, message = "Customer Handle is required")
    @NotNull
    private String handle;

    @NotNull
    @Email
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @NotNull
    private String password;

    @Size(min = 1, message = "First name is required")
    @NotNull
    private String firstName;

    private String lastName;


}
