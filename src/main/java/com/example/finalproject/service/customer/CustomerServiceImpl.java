package com.example.finalproject.service.customer;


import com.example.finalproject.entity.Customer;
import com.example.finalproject.model.customer.CustomerPartialReadDTO;
import com.example.finalproject.model.customer.CustomerPartialWriteDTO;
import com.example.finalproject.model.customer.CustomerWriteDTO;
import com.example.finalproject.model.customer.CustomerReadDTO;
import com.example.finalproject.model.post.PostReadDTO;
import com.example.finalproject.repository.customer.CustomerRepository;
import com.example.finalproject.service.post.PostService;
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
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final ModelMapper modelMapper;
    private final ModelMapper modelMapperIgnoresNull;

    private final PostService postService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               PostService postService,
                               @Qualifier("customerModelMapper") ModelMapper modelMapper,
                               @Qualifier("customerModelMapperIgnoresNull") ModelMapper modelMapperIgnoresNull) {
        this.customerRepository = customerRepository;
        this.postService = postService;
        this.modelMapper = modelMapper;
        this.modelMapperIgnoresNull = modelMapperIgnoresNull;
    }

    public Iterable<CustomerReadDTO> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> modelMapper.map(customer, CustomerReadDTO.class))
                .collect(Collectors.toList());
    }

    public CustomerReadDTO findById(Long customerId) {
        Customer customerToFind = customerRepository.findById(customerId);
        return modelMapper.map(customerToFind, CustomerReadDTO.class);
    }

    public Iterable<PostReadDTO> findCustomerPosts(Long customerId) {
        Customer validCustomer = customerRepository.findById(customerId);
        return postService.findPostsByCustomer(validCustomer.getId());
    }

    @Transactional
    public CustomerReadDTO save(CustomerWriteDTO customerWriteDTO) {
        Customer customerToSave = modelMapper.map(customerWriteDTO, Customer.class);
        Customer savedCustomer = customerRepository.save(customerToSave);
        return modelMapper.map(savedCustomer, CustomerReadDTO.class);
    }

    @Transactional
    public CustomerReadDTO updateCustomer(CustomerWriteDTO customerWriteDTO, Long customerId) {
        Customer customerToUpdate = modelMapper.map(customerWriteDTO, Customer.class);
        Customer updatedCustomer = customerRepository.updateCustomer(customerToUpdate, customerId);
        return modelMapper.map(updatedCustomer, CustomerReadDTO.class);
    }

    @Transactional
    public CustomerPartialReadDTO partialUpdate(CustomerPartialWriteDTO customerPartialWriteDTO, Long customerId) {
        Customer customerToUpdate = modelMapperIgnoresNull.map(customerPartialWriteDTO, Customer.class);
        Customer updatedCustomer = customerRepository.partialUpdate(customerToUpdate, customerId);
        return modelMapperIgnoresNull.map(updatedCustomer, CustomerPartialReadDTO.class);
    }

    @Transactional
    public void delete(Long customerId) {
        customerRepository.delete(customerId);
    }

}
