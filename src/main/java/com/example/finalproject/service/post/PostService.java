package com.example.finalproject.service.post;

import com.example.finalproject.model.post.PostPartialReadDTO;
import com.example.finalproject.model.post.PostPartialWriteDTO;
import com.example.finalproject.model.post.PostReadDTO;
import com.example.finalproject.model.post.PostWriteDTO;


public interface PostService {

    PostReadDTO save(PostWriteDTO postWriteDTO);

    Iterable<PostReadDTO> findAll();

    PostReadDTO findById(Long postId);

    Iterable<PostReadDTO> findPostsByCustomer(Long customerId);

    PostReadDTO update(PostWriteDTO newPost, Long postId);

    PostPartialReadDTO partialUpdate(PostPartialWriteDTO newPost, Long postId);

    void delete(Long postId);


}
