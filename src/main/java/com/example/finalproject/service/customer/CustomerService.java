package com.example.finalproject.service.customer;

import com.example.finalproject.entity.Customer;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


public interface CustomerService {


    List<Customer> findAll();

    Optional<Customer> findById(Long id);

    Optional<Customer> findByHandle(String username) throws EntityNotFoundException;

    Customer save(Customer customer);

    Customer updateCustomer(Customer customerToUpdate, Long customerId);

    void delete(Customer customerToDelete);

}
