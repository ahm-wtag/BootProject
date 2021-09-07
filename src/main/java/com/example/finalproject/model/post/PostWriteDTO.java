package com.example.finalproject.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PostWriteDTO {

    @Size(min = 1, message = "Title is required.")
    @NotNull
    private String title;

    private String body;

    @JsonIgnore
    private Long customerId;

}
