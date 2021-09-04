package com.example.finalproject.model.customer;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerPartialReadDTO {

    private Long id;

    private String handle;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

}
