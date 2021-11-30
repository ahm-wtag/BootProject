package com.example.finalproject.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@RequiredArgsConstructor
@Getter
@Setter
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "customer_id")
  private Long id;

  @Column(name = "customer_handle", unique = true)
  @NotNull
  private String handle;

  @Column(name = "customer_email", unique = true)
  @NotNull
  private String email;

  @Column(name = "customer_password")
  @NotNull
  private String password;

  @Column(name = "customer_first_name")
  @NotNull
  private String firstName;

  @Column(name = "customer_last_name")
  private String lastName;

  @Column(name = "customer_role")
  @NotNull
  private String role;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<Post> postList = new ArrayList<>();
}
