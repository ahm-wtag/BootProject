package com.example.finalproject.repository.customer;


import com.example.finalproject.entity.Customer;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class CustomerRepositoryImpl implements CustomerRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<Customer> findAll() {

        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);

        return query.getResultList();

    }

    public Optional<Customer> findById(Long id) {

        return Optional.ofNullable(entityManager.find(Customer.class, id));

    }

    public Optional<Customer> findByHandle(String handle) {
        try {
            TypedQuery<Customer> query = entityManager.createQuery("SELECT c from Customer c where c.handle=:handle", Customer.class);

            query.setParameter("handle", handle);

            return Optional.ofNullable(query.getSingleResult());

        } catch (NoResultException exception) {

            return Optional.empty();

        }
    }

    public Customer save(Customer customer) {

        entityManager.persist(customer);

        return customer;

    }

    public Customer updateCustomer(Customer newCustomer, Long customerId) {

        Optional<Customer> optionalCustomerToUpdate = findById(customerId);

        return optionalCustomerToUpdate.map(customer -> {

            customer.setHandle(newCustomer.getHandle());
            customer.setEmail(newCustomer.getEmail());
            customer.setPassword(newCustomer.getPassword());
            customer.setFirstName(newCustomer.getFirstName());
            customer.setLastName(newCustomer.getLastName());

            entityManager.merge(customer);

            return customer;

        }).orElseGet(() -> {

            newCustomer.setRole("ADMIN");

            entityManager.persist(newCustomer);

            return newCustomer;

        });

    }

    public void delete(Customer customerToDelete) {

        // Deleting posts associated with the customer, NO ORM!
        Query query = entityManager.createQuery("DELETE FROM Post p WHERE p.customer.id = :customerId");
        query.setParameter("customerId", customerToDelete.getId());
        query.executeUpdate();

        // Running in separate transaction from which customerToDelete was brought in
        customerToDelete = (entityManager.contains(customerToDelete)) ? customerToDelete : entityManager.merge(customerToDelete);
        entityManager.remove(customerToDelete);
    }


}
