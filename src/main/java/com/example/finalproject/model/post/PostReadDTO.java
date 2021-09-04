package com.example.finalproject.model.post;

import lombok.Data;

@Data
public class PostReadDTO {

    private Long id;

    private String title;

    private String body;

    private Long customerId;

}
