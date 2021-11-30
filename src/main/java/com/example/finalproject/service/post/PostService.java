package com.example.finalproject.service.post;

import java.util.List;
import java.util.Optional;

import com.example.finalproject.entity.Post;

public interface PostService {

  Post save(Post postWriteDTO);

  List<Post> findAll();

  Optional<Post> findById(Long postId);

  List<Post> findPostsByCustomer(Long customerId);

  Post update(Post oldPost, Long postId);

  void delete(Post postToDelete);
}
