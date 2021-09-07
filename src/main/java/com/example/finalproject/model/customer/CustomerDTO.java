package com.example.finalproject.model.customer;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CustomerDTO {

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
