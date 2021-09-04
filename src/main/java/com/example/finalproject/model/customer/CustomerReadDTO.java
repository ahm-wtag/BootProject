package com.example.finalproject.model.customer;


import lombok.Data;

@Data
public class CustomerReadDTO {

    private Long id;

    private String handle;

    private String email;

    private String password;

    private String firstName;

    private String lastName;


}
