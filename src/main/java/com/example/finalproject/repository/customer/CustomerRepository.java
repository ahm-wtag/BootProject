package com.example.finalproject.repository.customer;

import com.example.finalproject.entity.Customer;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;

@NoRepositoryBean
public interface CustomerRepository {

    Collection<Customer> findAll();

    Customer findById(Long id);

    Customer save(Customer customer);

    Customer updateCustomer(Customer customer, Long customerId);

    Customer partialUpdate(Customer customer, Long customerId);

    void delete(Long customerId);

}
