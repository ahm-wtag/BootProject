package com.example.finalproject.service.customer;

import com.example.finalproject.model.customer.CustomerPartialReadDTO;
import com.example.finalproject.model.customer.CustomerPartialWriteDTO;
import com.example.finalproject.model.customer.CustomerReadDTO;
import com.example.finalproject.model.customer.CustomerWriteDTO;
import com.example.finalproject.model.post.PostReadDTO;


public interface CustomerService {


    Iterable<CustomerReadDTO> findAll();

    CustomerReadDTO findById(Long id);

    Iterable<PostReadDTO> findCustomerPosts(Long customerId);

    CustomerReadDTO save(CustomerWriteDTO customerWriteDTO);

    CustomerReadDTO updateCustomer(CustomerWriteDTO customerWriteDTO, Long customerId);

    CustomerPartialReadDTO partialUpdate(CustomerPartialWriteDTO customerPartialWriteDTO, Long customerId);

    void delete(Long customerId);

}
