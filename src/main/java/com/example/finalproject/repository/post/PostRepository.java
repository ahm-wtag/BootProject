package com.example.finalproject.repository.post;

import com.example.finalproject.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    List<Post> findAll();

    Optional<Post> findById(Long postId);

    List<Post> findPostsByCustomer(Long customerId);

    Post update(Post newPost, Long postId);

    void delete(Post postToDelete);

}
