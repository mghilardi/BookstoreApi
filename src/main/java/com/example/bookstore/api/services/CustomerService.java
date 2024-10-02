package com.example.bookstore.api.services;

import com.example.bookstore.api.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    boolean isCustomerExists(Long id);

    Customer save(Customer customer);

    Optional<Customer> findById(Long id);

    List<Customer> listCustomers();

    void deleteById(Long id);
}