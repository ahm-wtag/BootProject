package com.example.finalproject.service.post;


import com.example.finalproject.entity.Post;
import com.example.finalproject.model.post.*;
import com.example.finalproject.repository.post.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Primary
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {


    private final PostRepository repository;
    private final ModelMapper modelMapper;
    private final ModelMapper modelMapperIgnoresNull;

    @Autowired
    public PostServiceImpl(PostRepository repository,
                           @Qualifier("postModelMapper") ModelMapper modelMapper,
                           @Qualifier("postModelMapperIgnoresNull") ModelMapper modelMapperIgnoresNull) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.modelMapperIgnoresNull = modelMapperIgnoresNull;
    }


    public Iterable<PostReadDTO> findPostsByCustomer(Long customerId) {
        return repository.findPostsByCustomer(customerId)
                .stream()
                .map(post -> modelMapper.map(post, PostReadDTO.class))
                .collect(Collectors.toList());
    }

    public PostReadDTO findById(Long postId) {
        Post postToFind = repository.findById(postId);
        return modelMapper.map(postToFind, PostReadDTO.class);
    }

    public Iterable<PostReadDTO> findAll() {
        return repository.findAll().stream()
                .map(post -> modelMapper.map(post, PostReadDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public PostReadDTO save(PostWriteDTO postWriteDTO) {
        Post postToSave = modelMapper.map(postWriteDTO, Post.class);
        postToSave.setId(null); // mapper put's customerId to post.Id and thus it become detatched
        Post savedPost = repository.save(postToSave);
        return modelMapper.map(savedPost, PostReadDTO.class);
    }

    @Transactional
    public PostReadDTO update(PostWriteDTO postWriteDTO, Long postId) {
        Post postToUpdate = modelMapper.map(postWriteDTO, Post.class);
        postToUpdate.setId(null);
        Post updatedPost = repository.update(postToUpdate, postId);
        return modelMapper.map(updatedPost, PostReadDTO.class);
    }

    @Transactional
    public PostPartialReadDTO partialUpdate(PostPartialWriteDTO postPartialWriteDTO,
                                            Long postId) {
        Post postToUpdate = modelMapperIgnoresNull.map(postPartialWriteDTO, Post.class);
        Post updatedPost = repository.partialUpdate(postToUpdate, postId);
        return modelMapperIgnoresNull.map(updatedPost, PostPartialReadDTO.class);
    }

    @Transactional
    public void delete(Long postId) {
        repository.delete(postId);
    }

}
