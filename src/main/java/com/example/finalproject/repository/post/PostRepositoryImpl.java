package com.example.finalproject.repository.post;


import com.example.finalproject.entity.Post;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Post save(Post post) {
        entityManager.persist(post);
        return post;
    }

    public List<Post> findAll() {
        TypedQuery<Post> query = entityManager.createQuery("SELECT post from Post post", Post.class);
        return query.getResultList();
    }

    public Post findById(Long postId) {
        try {
            Post post = entityManager.find(Post.class, postId);
            if (post == null) throw new EntityNotFoundException("Invalid postID");
            return post;
        } catch (EntityNotFoundException e) {
            throw new JpaObjectRetrievalFailureException(e);
        }
    }

    public Collection<Post> findPostsByCustomer(Long customerId) {

        TypedQuery<Post> query = entityManager.createQuery("SELECT p from Post p where p.customer.id=:customerId",Post.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();

    }

    public Post update(Post newPost, Long postId) {

        Optional<Post> postToUpdate = Optional.ofNullable(entityManager.find(Post.class, postId));

        return postToUpdate.map(post -> {
            post.setBody(newPost.getBody());
            post.setTitle(newPost.getTitle());
            post.setCustomer(newPost.getCustomer());
            entityManager.merge(post);
            return post;
        }).orElseGet(() -> {
            entityManager.persist(newPost);
            return newPost;
        });

    }

    public Post partialUpdate(Post newPost, Long postId) {

        Post postToUpdate = findById(postId);

        if (newPost.getTitle() != null) postToUpdate.setTitle(newPost.getTitle());
        if (newPost.getBody() != null) postToUpdate.setBody(newPost.getBody());
        if (newPost.getCustomer() != null) postToUpdate.setCustomer(newPost.getCustomer());

        entityManager.merge(postToUpdate);

        return postToUpdate;

    }

    public void delete(Long postId) {
        Post postToDelete = findById(postId);
        entityManager.remove(postToDelete);
    }


}
