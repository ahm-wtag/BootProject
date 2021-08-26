package com.example.finalproject.repository;


import com.example.finalproject.entity.Post;
import com.example.finalproject.exception.ApiRequestException;
import com.example.finalproject.model.PostUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PostRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Post save(Post post) {
        try {
            entityManager.persist(post);
            return post;
        } catch (Exception e) {
            final String MESSAGE = "Failed to save in database, only title and body and customer id required in RequestBody";
            throw new ApiRequestException(MESSAGE, HttpStatus.BAD_REQUEST, e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Post> findAll() {
        try {
            Query query = entityManager.createQuery("SELECT post from Post post", Post.class);
            return (List<Post>) query.getResultList();
        } catch (Exception e) {
            final String MESSAGE = "Failed to retrieve posts";
            throw new ApiRequestException(MESSAGE, e);
        }
    }

    public Post findById(Long postId) {
        try {
            Post post = entityManager.find(Post.class, postId);
            if (post == null) {
                final String MESSAGE = "Invalid postId";
                throw new ApiRequestException(MESSAGE);
            }
            return post;
        } catch (ApiRequestException e) {
            throw new ApiRequestException(e.getMessage(), HttpStatus.BAD_REQUEST, e);
        } catch (Exception e) {
            final String MESSAGE = "Failed to get required post";
            throw new ApiRequestException(MESSAGE, e);
        }
    }

    public List<Post> findPostsByCustomer(Long customerId) {
        try {
            Query query = entityManager.createQuery("SELECT p.id,p.body,p.title from Post p where p.customer.id=:customerId");
            query.setParameter("customerId", customerId);
            return query.getResultList();
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    public Post update(PostUpdateDTO newPost, Long postId) {
        Post postToUpdate = findById(postId);
        if (newPost.getTitle() != null) postToUpdate.setTitle(newPost.getTitle());
        if (newPost.getBody() != null) postToUpdate.setBody(newPost.getBody());

        try {
            entityManager.persist(postToUpdate);
            return postToUpdate;
        } catch (PersistenceException e) {
            throw new ApiRequestException(e.getCause().getMessage(), HttpStatus.BAD_REQUEST, e);
        }
    }

    public void delete(Long postId) {
        Post postToDelete = findById(postId);
        entityManager.remove(postToDelete);
    }

    public void deleteCustomerPosts(Long customerId) {
        Query query = entityManager.createQuery("DELETE from Post p where p.customer.id=:customerId");
        query.setParameter("customerId", customerId);
        query.executeUpdate();
    }


}
