package com.example.bookstore.api.services;

import com.example.bookstore.api.domain.Customer;
import com.example.bookstore.api.entities.CustomerEntity;
import com.example.bookstore.api.exception.ErrorMessages;
import com.example.bookstore.api.exception.ResourceNotFoundException;
import com.example.bookstore.api.repositories.CustomerRepository;
import com.example.bookstore.api.services.mappers.CustomerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(final CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public boolean isCustomerExists(Long id) {
        return customerRepository.existsById(id);
    }

    @Override
    public Customer save(Customer customer) {
        final CustomerEntity customerEntity = customerMapper.customerToCustomerEntity(customer);
        final CustomerEntity savedCustomerEntity = customerRepository.save(customerEntity);
        return customerMapper.customerEntityToCustomer(savedCustomerEntity);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        final Optional<CustomerEntity> foundCustomer = Optional.ofNullable(customerRepository.findById(id)
                                                                                             .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.NOT_EXIST_WITH_ID + id)));
        Customer customer = customerMapper.customerEntityToCustomer(foundCustomer.get());
        return Optional.of(customer);
    }

    @Override
    public List<Customer> listCustomers() {
        final List<CustomerEntity> foundCustomers = customerRepository.findAll(
                Sort.by(Sort.Direction.ASC, "name")
        );
        return foundCustomers.stream()
                             .map(customerMapper::customerEntityToCustomer)
                             .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}