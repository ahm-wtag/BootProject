package com.example.finalproject.repository.customer;

import com.example.finalproject.entity.Customer;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CustomerRepository {

    List<Customer> findAll();

    Optional<Customer> findById(Long id);

    Optional<Customer> findByHandle(String handle);

    Customer save(Customer customer);

    Customer updateCustomer(Customer customer, Long customerId);

    void delete(Customer customer);

}
