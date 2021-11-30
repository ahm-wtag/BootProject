package com.example.finalproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_id")
  private Long id;

  @Column(name = "post_title")
  @Size(min = 1, message = "Title is required.")
  @NotNull
  private String title;

  @Column(name = "post_body")
  private String body;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
  private Customer customer;
}
