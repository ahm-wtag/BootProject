package com.example.finalproject.controller;


import com.example.finalproject.model.customer.CustomerPartialReadDTO;
import com.example.finalproject.model.customer.CustomerPartialWriteDTO;
import com.example.finalproject.model.customer.CustomerWriteDTO;
import com.example.finalproject.model.customer.CustomerReadDTO;
import com.example.finalproject.model.post.PostReadDTO;
import com.example.finalproject.service.customer.CustomerService;
import com.example.finalproject.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Iterable<CustomerReadDTO>> findAll() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public CustomerReadDTO getCustomer(@PathVariable Long customerId) {
        return customerService.findById(customerId);
    }

    @GetMapping("/{customerId}/posts")
    public ResponseEntity<Iterable<PostReadDTO>> getCustomerPosts(@PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(customerService.findCustomerPosts(customerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerReadDTO> create(@Valid @RequestBody CustomerWriteDTO customerWriteDTO) {
        CustomerReadDTO savedCustomer = customerService.save(customerWriteDTO);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerReadDTO> update(@Valid @RequestBody CustomerWriteDTO newCustomer, @PathVariable Long customerId) {
        CustomerReadDTO updatedCustomer = customerService.updateCustomer(newCustomer, customerId);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerPartialReadDTO> partialUpdate(@Valid @RequestBody CustomerPartialWriteDTO newCustomer, @PathVariable Long customerId) {
        CustomerPartialReadDTO updatedCustomer = customerService.partialUpdate(newCustomer, customerId);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Object> delete(@PathVariable Long customerId) {
        customerService.delete(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
