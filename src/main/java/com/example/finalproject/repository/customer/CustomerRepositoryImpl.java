package com.example.finalproject.repository.customer;


import com.example.finalproject.entity.Customer;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

    public Customer findById(Long id) {
        try {
            Customer customer = entityManager.find(Customer.class, id);
            if (customer == null) throw new EntityNotFoundException("Invalid customer Id");
            return customer;
        } catch (EntityNotFoundException e) {
            throw new JpaObjectRetrievalFailureException(e);
        }
    }

    public Customer save(Customer customer) {
        entityManager.persist(customer);
        return customer;
    }

    public Customer updateCustomer(Customer newCustomer, Long customerId) {

        Optional<Customer> customerToUpdate = Optional.ofNullable(entityManager.find(Customer.class, customerId));

        return customerToUpdate.map( customer -> {
            customer.setHandle(newCustomer.getHandle());
            customer.setEmail(newCustomer.getEmail());
            customer.setPassword(newCustomer.getPassword());
            customer.setFirstName(newCustomer.getFirstName());
            customer.setLastName(newCustomer.getLastName());
            entityManager.merge(customer);
            return customer;
        }).orElseGet(() -> {
            entityManager.persist(newCustomer);
            return newCustomer;
        });

    }

    public Customer partialUpdate(Customer customer, Long customerId) {
        Customer customerToUpdate = findById(customerId);

        try {
            Field[] fields = customer.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals("postList")) continue;
                field.setAccessible(true);
                Object value = field.get(customer);
                if (value != null) {
                    BeanUtils.setProperty(customerToUpdate, field.getName(), value);
                }
            }

            entityManager.persist(customerToUpdate);

        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return customer;

    }


    public void delete(Long customerId) {
        Customer customerToDelete = findById(customerId);
        Query query = entityManager.createQuery("DELETE FROM Post p WHERE p.customer.id = :customerId");
        query.setParameter("customerId", customerId);
        query.executeUpdate();
        entityManager.remove(customerToDelete);
    }


}
