package com.example.bookstore.api.controller;

import com.example.bookstore.api.domain.Customer;
import com.example.bookstore.api.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody final Customer customer) {
        final Customer savedCustomer = customerService.save(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable final Long id,
                                                   @Valid @RequestBody final Customer customerDetails) {
        Optional<Customer> customerFound = customerService.findById(id);
        Customer customer = customerFound.get();
        customer.setName(customerDetails.getName());

        final Customer savedCustomer = customerService.save(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable final Long id) {
        final Optional<Customer> foundCustomer = customerService.findById(id);
        return foundCustomer.map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> listCustomers() {
        return new ResponseEntity<>(customerService.listCustomers(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteCustomer(@PathVariable final Long id) {
        customerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
