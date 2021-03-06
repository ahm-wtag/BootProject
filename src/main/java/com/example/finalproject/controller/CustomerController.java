package com.example.finalproject.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entity.Customer;
import com.example.finalproject.entity.Post;
import com.example.finalproject.model.customer.CustomerInput;
import com.example.finalproject.model.customer.CustomerOutput;
import com.example.finalproject.model.customer.CustomerPartialInput;
import com.example.finalproject.model.post.PostDTO;
import com.example.finalproject.model.post.PostWriteDTO;
import com.example.finalproject.service.customer.CustomerService;
import com.example.finalproject.service.post.PostService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

  private final CustomerService customerService;
  private final PostService postService;
  private final ModelMapper modelMapper;

  @Autowired
  public CustomerController(CustomerService customerService, PostService postService, ModelMapper modelMapper) {
    this.customerService = customerService;
    this.postService = postService;
    this.modelMapper = modelMapper;
  }

  @GetMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<List<CustomerOutput>> findAll() {

    List<CustomerOutput> customerOutputList = customerService.findAllOutput();

    return ResponseEntity.ok(customerOutputList);
  }

  //    @GetMapping("/{customerId}")
  //    public ResponseEntity<Customer> getCustomer(@PathVariable Long customerId, @Autowired CustomerRepository repository) {
  //
  //        return new ResponseEntity<>(repository.getById(customerId),HttpStatus.OK);
  //
  //    }

  @GetMapping("/{userName}")
  public ResponseEntity<CustomerOutput> getCustomerByHandle(@PathVariable String userName) {

    Optional<CustomerOutput> optionalCustomer = customerService.findByHandleOutput(userName);

    return optionalCustomer.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound()
            .build());
  }

  @GetMapping("/{customerId}/posts")
  public ResponseEntity<List<PostDTO>> getCustomerPosts(@PathVariable Long customerId) {

    Optional<Customer> optionalValidCustomer = customerService.findById(customerId);

    return optionalValidCustomer.map(customer -> {

          List<Post> customerPosts = postService.findPostsByCustomer(customer.getId());

          List<PostDTO> customerPostDTOs = customerPosts.stream()
              .map(post -> modelMapper.map(post, PostDTO.class))
              .collect(Collectors.toList());

          return new ResponseEntity<>(customerPostDTOs, HttpStatus.OK);
        })
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public ResponseEntity<CustomerOutput> create(@Valid @RequestBody CustomerInput customerInput) {

    CustomerOutput createdCustomerOutput = customerService.createAndOutput(customerInput);

    return ResponseEntity.ok(createdCustomerOutput);
  }

  @PostMapping("/{customerId}/posts")
  @PreAuthorize("@securityUtil.authorizationCheck(principal,#customerId)")
  public ResponseEntity<PostDTO> create(@Valid @RequestBody PostWriteDTO postWriteDTO, @PathVariable Long customerId) {

    Optional<Customer> loggedInCustomer = customerService.findById(customerId);

    Post postToSave = modelMapper.map(postWriteDTO, Post.class);
    //      loggedInCustomer is always non-null.
    loggedInCustomer.ifPresent(postToSave::setCustomer);

    PostDTO savedPost = modelMapper.map(postService.save(postToSave), PostDTO.class);

    return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
  }

  @PatchMapping("/{customerId}")
  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or @securityUtil.authorizationCheck(principal,#customerId)")
  public ResponseEntity<CustomerOutput> update(@Valid @RequestBody CustomerPartialInput customerUpdates,
      @PathVariable Long customerId)
  {

    return customerService.updateCustomerAndOutput(customerUpdates, customerId)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound()
            .build());
  }

  @PutMapping("/{customerId}/posts/{postId}")
  @PreAuthorize("@securityUtil.authorizationCheck(principal,#customerId)")
  public ResponseEntity<?> update(@Valid @RequestBody PostWriteDTO postWriteDTO, @PathVariable Long postId,
      @PathVariable Long customerId)
  {

    Optional<Post> optionalPost = postService.findById(postId);

    postWriteDTO.setCustomerId(customerId);

    Post newPost = modelMapper.map(postWriteDTO, Post.class);

    return optionalPost.map(post -> {
          if (post.getCustomer()
              .getId()
              .equals(customerId))
          {

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

    return postToDelete.map(post -> {

          if (post.getCustomer()
              .getId()
              .equals(customerId))
          {
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

    return customerToDelete.map(customer -> {
          customerService.delete(customer);
          return ResponseEntity.noContent()
              .build();
        })
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
