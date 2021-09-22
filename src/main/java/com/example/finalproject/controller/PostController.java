package com.example.finalproject.controller;


import com.example.finalproject.entity.Post;
import com.example.finalproject.model.post.*;
import com.example.finalproject.service.post.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/posts")
@CrossOrigin( origins = "http://localhost:4200")
public class PostController {

    private final PostService postService;
    private final ModelMapper modelMapper;

    @Autowired
    public PostController(PostService postService,
                          ModelMapper modelMapper) {
        this.postService = postService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> findAll() {

        List<PostDTO> postDTOList = postService.findAll()
                .stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }


    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> findPost(@PathVariable Long postId) {

        Optional<Post> postToFind = postService.findById(postId);

        return postToFind
                .map(post -> new ResponseEntity<>(modelMapper.map(post, PostDTO.class), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }



    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> deletePostByAdmin(@PathVariable Long postId) {

        Optional<Post> postToDelete = postService.findById(postId);

        return postToDelete
                .map(post -> {
                    postService.delete(post);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}