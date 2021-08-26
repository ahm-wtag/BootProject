package com.example.finalproject.service;


import com.example.finalproject.entity.Customer;
import com.example.finalproject.model.CustomerUpdateDTO;
import com.example.finalproject.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository repository;

    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    public Customer findByHandle(String handle) {
        return repository.findByHandle(handle);
    }

    public Customer findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long customerId) {
        repository.delete(customerId);
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Customer updateCustomer(CustomerUpdateDTO newCustomer, Long customerId) {
        return repository.updateCustomer(newCustomer, customerId);
    }

}
