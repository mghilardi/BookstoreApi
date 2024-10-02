package com.example.bookstore.api.services;

import com.example.bookstore.api.domain.Customer;
import com.example.bookstore.api.entities.CustomerEntity;
import com.example.bookstore.api.exception.ResourceNotFoundException;
import com.example.bookstore.api.repositories.CustomerRepository;
import com.example.bookstore.api.services.mappers.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static com.example.bookstore.api.stub.StubData.testCustomer;
import static com.example.bookstore.api.stub.StubData.testCustomerEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl underTest;

    @BeforeEach
    void setUp() {
    }

    @Test
    void save() {
        final Customer customer = testCustomer();
        final CustomerEntity customerEntity = testCustomerEntity();

        when(customerMapper.customerToCustomerEntity(eq(customer))).thenCallRealMethod();
        when(customerRepository.save(eq(customerEntity))).thenReturn(customerEntity);
        when(customerMapper.customerEntityToCustomer(eq(customerEntity))).thenCallRealMethod();

        final Customer result = underTest.save(customer);
        assertEquals(customer, result);
    }

    @Test
    void findById_throwsResourceNotFoundException() {
        final Long id = 1L;
        when(customerRepository.findById(eq(id))).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            underTest.findById(id);
        });
        assertEquals("Resource does not exist with id: 1", exception.getMessage());
    }

    @Test
    void findById_returnsCustomerWhenExists() {
        final Customer customer = testCustomer();
        final CustomerEntity customerEntity = testCustomerEntity();

        when(customerRepository.findById(eq(customer.getId()))).thenReturn(Optional.of(customerEntity));
        when(customerMapper.customerEntityToCustomer(eq(customerEntity))).thenCallRealMethod();

        final Optional<Customer> result = underTest.findById(customer.getId());
        assertEquals(Optional.of(customer), result);
    }

    @Test
    void listCustomers_returnsEmptyListWhenNoCustomersExist() {
        final CustomerEntity customerEntity = testCustomerEntity();
        when(customerRepository.findAll(any(Sort.class))).thenReturn(List.of(customerEntity));
        final List<Customer> result = underTest.listCustomers();
        assertEquals(1, result.size());
    }

    @Test
    void listCustomers_returnsCustomersWhenExist() {
        final CustomerEntity customerEntity = testCustomerEntity();
        when(customerRepository.findAll(any(Sort.class))).thenReturn(List.of(customerEntity));
        final List<Customer> result = underTest.listCustomers();
        assertEquals(1, result.size());
    }

    @Test
    void isCustomerExists_returnsFalseWhenCustomerDoesntExist() {
        when(customerRepository.existsById(any())).thenReturn(false);
        final boolean result = underTest.isCustomerExists(testCustomer().getId());
        assertFalse(result);
    }

    @Test
    void deleteById() {
        final Long id = 1L;
        underTest.deleteById(id);
        verify(customerRepository, times(1)).deleteById(eq(id));
    }
}