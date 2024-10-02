package com.example.bookstore.api.services.mappers;

import com.example.bookstore.api.domain.Customer;
import com.example.bookstore.api.entities.CustomerEntity;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@NoArgsConstructor
public class CustomerMapper {

    public CustomerEntity customerToCustomerEntity(Customer customer) {
        return CustomerEntity.builder()
                             .id(customer.getId())
                             .name(customer.getName())
                             .created_at(customer.getCreated_at())
//                         .orders(customer.getOrders())
                             .build();
    }

    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return Customer.builder()
                       .id(customerEntity.getId())
                       .name(customerEntity.getName())
                       .created_at(customerEntity.getCreated_at())
//                         .orders(customer.getOrders())
                       .build();
    }
}