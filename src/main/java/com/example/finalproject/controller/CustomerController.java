package com.example.finalproject.controller;


import com.example.finalproject.entity.Customer;
import com.example.finalproject.entity.Post;
import com.example.finalproject.model.customer.CustomerWriteDTO;
import com.example.finalproject.model.customer.CustomerDTO;
import com.example.finalproject.model.post.PostDTO;
import com.example.finalproject.model.post.PostWriteDTO;
import com.example.finalproject.service.customer.CustomerService;
import com.example.finalproject.service.post.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final PostService postService;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerController(CustomerService customerService,
                              PostService postService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.postService = postService;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CustomerDTO>> findAll() {

        List<CustomerDTO> customerDTOList = customerService.findAll()
                .stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(customerDTOList, HttpStatus.OK);

    }

//    @GetMapping("/{customerId}")
//    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long customerId) {
//
//        Optional<Customer> optionalCustomerToFind = customerService.findById(customerId);
//
//        return optionalCustomerToFind
//                .map(customer -> {
//                    CustomerDTO foundCustomer = modelMapper.map(customer, CustomerDTO.class);
//                    return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
//                })
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//
//    }


    @GetMapping("/{userName}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable String userName) {

        Optional<Customer> optionalCustomer = customerService.findByHandle(userName);

        return optionalCustomer
                .map(customer -> {
                    CustomerDTO foundCustomer = modelMapper.map(customer, CustomerDTO.class);
                    return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/{customerId}/posts")
    public ResponseEntity<List<PostDTO>> getCustomerPosts(@PathVariable Long customerId) {

        Optional<Customer> optionalValidCustomer = customerService.findById(customerId);

        return optionalValidCustomer
                .map(customer -> {

                    List<Post> customerPosts = postService.findPostsByCustomer(customer.getId());

                    List<PostDTO> customerPostDTOs = customerPosts
                            .stream()
                            .map(post -> modelMapper.map(post, PostDTO.class))
                            .collect(Collectors.toList());

                    return new ResponseEntity<>(customerPostDTOs, HttpStatus.OK);

                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping
    public ResponseEntity<CustomerDTO> create(@Valid @RequestBody CustomerWriteDTO customerWriteDTO) {

        Customer customerToSave = modelMapper.map(customerWriteDTO, Customer.class);

        CustomerDTO savedCustomerDTO = modelMapper.map(customerService.save(customerToSave), CustomerDTO.class);

        return new ResponseEntity<>(savedCustomerDTO, HttpStatus.CREATED);

    }

    @PostMapping("/{customerId}/posts")
    @PreAuthorize("@securityUtil.authorizationCheck(principal,#customerId)")
    public ResponseEntity<PostDTO> create(@Valid @RequestBody PostWriteDTO postWriteDTO,
                                          @PathVariable Long customerId) {

        Optional<Customer> loggedInCustomer = customerService.findById(customerId);

        Post postToSave = modelMapper.map(postWriteDTO, Post.class);
//      loggedInCustomer is always non-null.
        loggedInCustomer.ifPresent(postToSave::setCustomer);

        PostDTO savedPost = modelMapper.map(postService.save(postToSave), PostDTO.class);

        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);

    }


    @PutMapping("/{customerId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or @securityUtil.authorizationCheck(principal,#customerId)")
    public ResponseEntity<CustomerDTO> update(@Valid @RequestBody CustomerWriteDTO newCustomer, @PathVariable Long customerId) {

        Customer customerUpdates = modelMapper.map(newCustomer, Customer.class);

        Customer updatedCustomer = customerService.updateCustomer(customerUpdates, customerId);

        CustomerDTO updatedCustomerDTO = modelMapper.map(updatedCustomer, CustomerDTO.class);

        if (updatedCustomerDTO.getId().equals(customerId)) {
            return new ResponseEntity<>(updatedCustomerDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(updatedCustomerDTO, HttpStatus.CREATED);

    }

    @PutMapping("/{customerId}/posts/{postId}")
    @PreAuthorize("@securityUtil.authorizationCheck(principal,#customerId)")
    public ResponseEntity<?> update(@Valid @RequestBody PostWriteDTO postWriteDTO,
                                    @PathVariable Long postId,
                                    @PathVariable Long customerId) {

        Optional<Post> optionalPost = postService.findById(postId);

        postWriteDTO.setCustomerId(customerId);

        Post newPost = modelMapper.map(postWriteDTO, Post.class);

        return optionalPost
                .map(post -> {
                    if (post.getCustomer().getId().equals(customerId)) {

                        Post updatedPost = postService.update(newPost, postId);
                        // post exists and user has authority over it
                        return new ResponseEntity<>(modelMapper.map(updatedPost, PostDTO.class), HttpStatus.OK);

                    }
                    // post exists and user doesn't have authority over it
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);

                })
                .orElseGet(() -> {
                    // post does not exist
                    Post createdPost = postService.update(newPost, postId);
                    return new ResponseEntity<>(modelMapper.map(createdPost, PostDTO.class), HttpStatus.CREATED);

                });
    }

    @DeleteMapping("/{customerId}/posts/{postId}")
    @PreAuthorize("@securityUtil.authorizationCheck(principal,#customerId)")
    public ResponseEntity<Object> deleteUserPost(@PathVariable Long postId, @PathVariable Long customerId) {

        Optional<Post> postToDelete = postService.findById(postId);

        return postToDelete
                .map(post -> {

                    if (post.getCustomer().getId().equals(customerId)) {
                        postService.delete(post);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                    }

                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);


                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }


    @DeleteMapping("/{customerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @securityUtil.authorizationCheck(principal,#customerId)")
    public ResponseEntity<Object> delete(@PathVariable Long customerId) {


        Optional<Customer> customerToDelete = customerService.findById(customerId);

        return customerToDelete
                .map(customer -> {
                    customerService.delete(customer);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }


}
