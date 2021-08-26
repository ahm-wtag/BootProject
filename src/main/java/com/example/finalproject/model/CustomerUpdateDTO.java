package com.example.finalproject.model;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customer")
public class CustomerUpdateDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "customer_handle", unique = true)
    @Size(min = 1, message = "Customer Handle is required")
    private String handle;

    @Column(name = "customer_email", unique = true)
    @Email(message = "Email is invalid")
    private String email;

    @Column(name = "customer_password")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Column(name = "customer_first_name")
    @Size(min = 1, message = "First name is required")
    private String firstName;

    @Column(name = "customer_last_name")
    private String lastName;


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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", handle='" + handle + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
