package com.example.finalproject.controller;


import com.example.finalproject.entity.Post;
import com.example.finalproject.exception.ApiRequestException;
import com.example.finalproject.model.PostUpdateDTO;
import com.example.finalproject.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);


    PostService postService;

    @Autowired
    public PostController( PostService postService ) {
        this.postService = postService;
    }



    @GetMapping
    public ResponseEntity<Iterable<Post>> findAll() {
        try {
            return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.warn("exception here");
            throw new ApiRequestException(e.getMessage(), e);
        }
    }


    @PostMapping
    public ResponseEntity<Post> create(@Valid @RequestBody Post post) {

        Post savedPost = postService.save(post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);

    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> findPost(@PathVariable("postId") Long postId) {
        return new ResponseEntity<>(postService.findById(postId), HttpStatus.OK);
    }


    @PutMapping("/{postId}")
    public ResponseEntity<Post> update(@Valid @RequestBody PostUpdateDTO newPost, @PathVariable("postId") Long postId) {
        Post updatedPost = postService.update(newPost, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Object> delete(@PathVariable("postId") Long postId) {
        postService.delete(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}