package com.example.finalproject.service.customer;

import java.util.List;
import java.util.Optional;

import com.example.finalproject.entity.Customer;
import com.example.finalproject.model.customer.CustomerInput;
import com.example.finalproject.model.customer.CustomerOutput;
import com.example.finalproject.model.customer.CustomerPartialInput;

public interface CustomerService {

  Customer mapToEntity(CustomerInput customerInput);

  CustomerOutput mapToOutput(Customer customer);

  List<CustomerOutput> findAllOutput();

  List<Customer> findAll();

  Optional<Customer> findById(Long id);

  Optional<Customer> findByHandle(String username);

  Optional<CustomerOutput> findByHandleOutput(String username);

  Customer create(Customer customerToCreate);

  CustomerOutput createAndOutput(CustomerInput customerInput);

  Optional<Customer> updateCustomer(CustomerPartialInput customerInput, Long customerId);

  Optional<CustomerOutput> updateCustomerAndOutput(CustomerPartialInput customerInput, Long customerId);

  void delete(Customer customerToDelete);
}
