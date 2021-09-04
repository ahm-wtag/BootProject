package com.example.finalproject.model.post;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class PostPartialWriteDTO {

    @Size(min = 1, message = "Post title is required")
    private String title;

    private String body;

    private Long customerId;

}
