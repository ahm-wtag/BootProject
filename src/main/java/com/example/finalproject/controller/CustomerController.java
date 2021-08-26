package com.example.finalproject.controller;


import com.example.finalproject.entity.Customer;
import com.example.finalproject.entity.Post;
import com.example.finalproject.exception.ApiRequestException;
import com.example.finalproject.model.CustomerUpdateDTO;
import com.example.finalproject.service.CustomerService;
import com.example.finalproject.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    PostService postService;


    static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping
    public Iterable<Customer> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable Long customerId) {
        try {
            return customerService.findById(customerId);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @GetMapping("/{customerId}/posts")
    public ResponseEntity<Iterable<Post>> getCustomerPosts(@PathVariable("customerId") Long customerId) {
        try {
            Customer validCustomer = customerService.findById(customerId);
            return new ResponseEntity<Iterable<Post>>(postService.findPostsByCustomer(validCustomer.getId()), HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @PostMapping
    public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer) {
        Customer savedCustomer = customerService.save(customer);
        return new ResponseEntity<Customer>(savedCustomer, HttpStatus.CREATED);
    }


    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> update(@Valid @RequestBody CustomerUpdateDTO newCustomer, @PathVariable Long customerId) {

        try {
            Customer updatedCustomer = customerService.updateCustomer(newCustomer, customerId);
            return new ResponseEntity<Customer>(updatedCustomer, HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiRequestException(e.getCause().getClass().toString(), HttpStatus.BAD_REQUEST, e);
        }
    }


    @DeleteMapping("/{customerId}")
    public ResponseEntity<Object> delete(@PathVariable Long customerId) {
        customerService.delete(customerId);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }


}
