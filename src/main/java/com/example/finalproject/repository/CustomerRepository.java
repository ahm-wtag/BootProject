package com.example.finalproject.repository;


import com.example.finalproject.entity.Customer;
import com.example.finalproject.exception.ApiRequestException;
import com.example.finalproject.model.CustomerUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class CustomerRepository {


//    static final Logger log = Logger.getLogger(CustomerRepository.class);
    @PersistenceContext
    EntityManager entityManager;

    public Customer save(Customer customer) {
        try {
            entityManager.persist(customer);
        } catch (PersistenceException e) {
            throw new ApiRequestException(e.getCause().getClass().toString(), HttpStatus.BAD_REQUEST, e);
        }
        return customer;
    }

    public Customer findByHandle(String handle) {
        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer  c where c.handle=:handle", Customer.class);
        query.setParameter("handle", handle);
        return query.getSingleResult();
    }

    public Customer findById(Long id) {
        try {
            Query query = entityManager.createQuery("select c from Customer c where c.id = :id");
            query.setParameter("id", id);

            return (Customer) query.getSingleResult();
        } catch (PersistenceException e) {
            throw new ApiRequestException("Invalid Customer Id",HttpStatus.BAD_REQUEST,e);
        }
    }

    public List<Customer> findAll() {
        Query query = entityManager.createQuery("SELECT c from Customer c");
        return query.getResultList();
    }

    public Customer updateCustomer(CustomerUpdateDTO newCustomer, Long customerId) {

        Customer customerToUpdate = findById(customerId);

        if (newCustomer.getHandle() != null) customerToUpdate.setHandle(newCustomer.getHandle());
        if (newCustomer.getEmail() != null) customerToUpdate.setEmail(newCustomer.getEmail());
        if (newCustomer.getPassword() != null) customerToUpdate.setPassword(newCustomer.getPassword());
        if (newCustomer.getFirstName() != null) customerToUpdate.setFirstName(newCustomer.getFirstName());
        if (newCustomer.getLastName() != null) customerToUpdate.setLastName(newCustomer.getLastName());

        entityManager.persist(customerToUpdate);
        return customerToUpdate;
    }


    public void delete(Long customerId) {
        System.out.println("query1");
        Customer customerToDelete = findById(customerId);
        Query query = entityManager.createQuery("DELETE from Post p where p.customer.id = :customerId");
        query.setParameter("customerId",customerId);
        System.out.println("query2");
        query.executeUpdate();
        System.out.println("query3");
        entityManager.remove(customerToDelete);
    }


}
