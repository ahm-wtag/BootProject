package com.example.finalproject.service;


import com.example.finalproject.entity.Post;
import com.example.finalproject.model.PostUpdateDTO;
import com.example.finalproject.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostService {

    @Autowired
    PostRepository repository;

    public Post save(Post post) {
        return repository.save(post);
    }

    public List<Post> findAll() {
        return repository.findAll();
    }

    public Post findById(Long postId) {
        return repository.findById(postId);
    }

    public List<Post> findPostsByCustomer(Long customerId) {
        return repository.findPostsByCustomer(customerId);
    }

    public Post update(PostUpdateDTO newPost, Long postId) {
        return repository.update(newPost, postId);
    }

    public void delete(Long postId) {
        repository.delete(postId);
    }

    public void deleteCustomerPosts(Long customerId) {
        repository.deleteCustomerPosts(customerId);
    }

}
