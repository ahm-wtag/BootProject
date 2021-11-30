package com.example.finalproject.repository.customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.finalproject.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Optional<Customer> findByHandle(String handle);
}
