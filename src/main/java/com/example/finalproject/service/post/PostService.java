package com.example.finalproject.service.post;

import com.example.finalproject.entity.Post;

import java.util.List;
import java.util.Optional;


public interface PostService {

    Post save(Post postWriteDTO);

    List<Post> findAll();

    Optional<Post> findById(Long postId);

    List<Post> findPostsByCustomer(Long customerId);

    Post update(Post oldPost, Long postId);

    void delete(Post postToDelete);


}
