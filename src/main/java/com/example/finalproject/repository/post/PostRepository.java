package com.example.finalproject.repository.post;

import java.util.List;
import java.util.Optional;

import com.example.finalproject.entity.Post;

public interface PostRepository {

  Post save(Post post);

  List<Post> findAll();

  Optional<Post> findById(Long postId);

  List<Post> findPostsByCustomer(Long customerId);

  Post update(Post newPost, Long postId);

  void delete(Post postToDelete);
}
