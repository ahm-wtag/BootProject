package com.example.finalproject.util;

import com.example.finalproject.entity.Customer;
import com.example.finalproject.service.customer.CustomerService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("securityUtil")
public class SecurityUtil {

    private final CustomerService customerService;

    public SecurityUtil(CustomerService customerService) {
        this.customerService = customerService;
    }

    public boolean authorizationCheck(String principal, Long customerId) {

        Optional<Customer> optionalCustomer = customerService.findById(customerId);

        return optionalCustomer
                .map(customer -> customer.getHandle().equals(principal))
                .orElseGet(() -> false);

    }


}
