package com.example.finalproject.repository.post;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.example.finalproject.entity.Post;

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

  public Optional<Post> findById(Long postId) {

    return Optional.ofNullable(entityManager.find(Post.class, postId));
  }

  public List<Post> findPostsByCustomer(Long customerId) {

    TypedQuery<Post> query = entityManager.createQuery("SELECT p from Post p where p.customer.id=:customerId",
        Post.class);
    query.setParameter("customerId", customerId);
    return query.getResultList();
  }

  public Post update(Post newPost, Long postId) {

    Optional<Post> postToUpdate = Optional.ofNullable(entityManager.find(Post.class, postId));

    newPost.setId(null);

    return postToUpdate.map(post -> {

          post.setBody(newPost.getBody());
          post.setTitle(newPost.getTitle());

          entityManager.merge(post);

          return post;
        })
        .orElseGet(() -> {

          entityManager.persist(newPost);

          return newPost;
        });
  }

  public void delete(Post postToDelete) {

    postToDelete = (entityManager.contains(postToDelete)) ? postToDelete : entityManager.merge(postToDelete);

    entityManager.remove(postToDelete);
  }
}
