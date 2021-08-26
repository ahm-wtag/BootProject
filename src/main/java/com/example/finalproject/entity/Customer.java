package com.example.finalproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "customer_handle", unique = true)
    @Size(min = 1, message = "Customer Handle is required")
    @NotNull
    private String handle;

    @Column(name = "customer_email", unique = true)
    @NotNull
    private String email;

    @Column(name = "customer_password")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @NotNull
    private String password;

    @Column(name = "customer_first_name")
    @Size(min = 1, message = "First name is required")
    private String firstName;

    @Column(name = "customer_last_name")
    private String lastName;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Post> postsList = new ArrayList<Post>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonIgnore
    public List<Post> getPostsList() {
        return postsList;
    }

    public void setPostsList(List<Post> postsList) {
        this.postsList = postsList;
    }


}
