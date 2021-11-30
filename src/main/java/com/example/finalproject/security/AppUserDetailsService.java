package com.example.finalproject.security;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.finalproject.entity.Customer;
import com.example.finalproject.service.customer.CustomerService;

@Service
public class AppUserDetailsService implements UserDetailsService {

  private final CustomerService customerService;

  private final PasswordEncoder encoder;

  public AppUserDetailsService(CustomerService customerService, PasswordEncoder encoder) {

    this.customerService = customerService;
    this.encoder = encoder;
  }

  @Override
  public UserDetails loadUserByUsername(String handle) throws UsernameNotFoundException {

    Optional<Customer> optionalCustomerToFind = customerService.findByHandle(handle);

    Customer loggedInCustomer = optionalCustomerToFind.orElseThrow(
        () -> new UsernameNotFoundException("Invalid username"));

    Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();

    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + loggedInCustomer.getRole()));

    return User.builder()
        .username(loggedInCustomer.getHandle())
        .password(encoder.encode(loggedInCustomer.getPassword()))
        .authorities(grantedAuthorities)
        .build();
  }
}
