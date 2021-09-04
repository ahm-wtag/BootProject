package com.example.finalproject.controller;


import com.example.finalproject.model.post.*;
import com.example.finalproject.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<Iterable<PostReadDTO>> findAll() {
        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostReadDTO> create(@Valid @RequestBody PostWriteDTO postWriteDTO) {
        PostReadDTO savedPost = postService.save(postWriteDTO);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostReadDTO> findPost(@PathVariable Long postId) {
        return new ResponseEntity<>(postService.findById(postId), HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostReadDTO> update(@Valid @RequestBody PostWriteDTO newPost, @PathVariable Long postId) {
        PostReadDTO updatedPost = postService.update(newPost, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostPartialReadDTO> partialUpdate(@Valid @RequestBody PostPartialWriteDTO newPost,
                                                            @PathVariable Long postId) {
        PostPartialReadDTO updatedPost = postService.partialUpdate(newPost,postId);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }


    @DeleteMapping("/{postId}")
    public ResponseEntity<Object> delete(@PathVariable Long postId) {
        postService.delete(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}