package com.example.finalproject.service.customer;


import com.example.finalproject.entity.Customer;
import com.example.finalproject.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;

    }

    public List<Customer> findAll() {

        return customerRepository.findAll();

    }

    public Optional<Customer> findById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public Optional<Customer> findByHandle(String username) {

        return customerRepository.findByHandle(username);

    }

    @Transactional
    public Customer save(Customer customerToSave) {

        customerToSave.setRole("USER");

        return customerRepository.save(customerToSave);

    }

    @Transactional
    public Customer updateCustomer(Customer customerToUpdate, Long customerId) {

        return customerRepository.updateCustomer(customerToUpdate, customerId);

    }

    @Transactional
    public void delete(Customer customerToDelete) {

        customerRepository.delete(customerToDelete);

    }

}
