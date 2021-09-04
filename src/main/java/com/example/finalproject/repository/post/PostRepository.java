package com.example.finalproject.repository.post;

import com.example.finalproject.entity.Post;

import java.util.Collection;
import java.util.List;

public interface PostRepository {

    Post save(Post post);

    List<Post> findAll();

    Post findById(Long postId);

    Collection<Post> findPostsByCustomer(Long customerId);

    Post update(Post newPost, Long postId);

    Post partialUpdate(Post newPost, Long postId);

    void delete(Long postId);

}
