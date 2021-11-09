package com.example.finalproject.service.customer;


import com.example.finalproject.entity.Customer;
import com.example.finalproject.model.customer.CustomerInput;
import com.example.finalproject.model.customer.CustomerOutput;
import com.example.finalproject.model.customer.CustomerPartialInput;
import com.example.finalproject.repository.customer.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }


    public Customer mapToEntity(CustomerInput customerInput) {
        return modelMapper.map(customerInput,Customer.class);
    }

    public CustomerOutput mapToOutput(Customer customer) {
        return modelMapper.map(customer,CustomerOutput.class);
    }

    public List<Customer> findAll() {

        return repository.findAll();

    }

    public List<CustomerOutput> findAllOutput() {
        return findAll()
                .stream()
                .map(customer -> modelMapper.map(customer,CustomerOutput.class))
                .collect(Collectors.toList());
    }

    public Optional<Customer> findById(Long customerId) {
        return repository.findById(customerId);
    }

    public Optional<Customer> findByHandle(String username) {

        return repository.findByHandle(username);

    }

    public Optional<CustomerOutput> findByHandleOutput(String username) {
        return findByHandle(username)
                .map(this::mapToOutput);
    }

    @Transactional
    public Customer create(Customer customerToSave) {

        customerToSave.setRole("USER");

        return repository.save(customerToSave);

    }

    @Transactional
    public CustomerOutput createAndOutput(CustomerInput customerInputToSave) {

        Customer customerToSave = mapToEntity(customerInputToSave);

        Customer savedCustomer = create(customerToSave);

        return mapToOutput(savedCustomer);

    }

    @Transactional
    public Optional<Customer> updateCustomer(CustomerPartialInput customerUpdates, Long customerId) {

        Optional<Customer> optionalCustomerToUpdate = findById(customerId);

        return optionalCustomerToUpdate
                .map(customerToUpdate -> {

                    if ( customerUpdates.getHandle() != null ) customerToUpdate.setHandle(customerUpdates.getHandle());
                    if ( customerUpdates.getEmail() != null ) customerToUpdate.setEmail(customerUpdates.getEmail());
                    if ( customerUpdates.getPassword() != null ) customerToUpdate.setPassword(customerUpdates.getPassword());
                    if ( customerUpdates.getFirstName() != null ) customerToUpdate.setFirstName(customerUpdates.getFirstName());
                    if ( customerUpdates.getLastName() != null ) customerToUpdate.setLastName(customerUpdates.getLastName());

                    return customerToUpdate;
                });
    }

    public Optional<CustomerOutput> updateCustomerAndOutput(CustomerPartialInput customerUpdates, Long customerId) {

        return updateCustomer(customerUpdates,customerId)
                .map(this::mapToOutput);

    }


    @Transactional
    public void delete(Customer customerToDelete) {

        repository.delete(customerToDelete);

    }

}
