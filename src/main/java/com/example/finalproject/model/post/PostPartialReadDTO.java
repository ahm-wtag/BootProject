package com.example.finalproject.model.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostPartialReadDTO {

    private Long id;

    private String title;

    private String body;

    private Long customerId;

}
