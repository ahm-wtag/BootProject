package com.example.finalproject.service.post;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.finalproject.entity.Post;
import com.example.finalproject.repository.post.PostRepository;

@Service
@Primary
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

  private final PostRepository repository;

  @Autowired
  public PostServiceImpl(PostRepository repository) {

    this.repository = repository;
  }

  public List<Post> findPostsByCustomer(Long customerId) {

    return repository.findPostsByCustomer(customerId);
  }

  public Optional<Post> findById(Long postId) {

    return repository.findById(postId);
  }

  public List<Post> findAll() {

    return repository.findAll();
  }

  @Transactional
  public Post save(Post postToSave) {

    return repository.save(postToSave);
  }

  @Transactional
  public Post update(Post newPost, Long postId) {

    return repository.update(newPost, postId);
  }

  @Transactional
  public void delete(Post postToDelete) {

    repository.delete(postToDelete);
  }
}
