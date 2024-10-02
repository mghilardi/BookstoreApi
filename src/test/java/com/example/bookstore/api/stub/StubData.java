package com.example.bookstore.api.stub;

import com.example.bookstore.api.domain.Book;
import com.example.bookstore.api.domain.Customer;
import com.example.bookstore.api.entities.BookEntity;
import com.example.bookstore.api.entities.CustomerEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public final class StubData {

    private StubData() {
    }

    public static Book testBook() {
        return Book.builder()
                   .id(1L)
                   .isbn("1234567890111")
                   .author("author X")
                   .title("New book")
                   .price(BigDecimal.valueOf(99.99))
                   .quantity(100)
                   .description("New book description")
                   .build();
    }

    public static BookEntity testBookEntity() {
        return BookEntity.builder()
                         .id(1L)
                         .isbn("1234567890111")
                         .author("author X")
                         .title("New book")
                         .price(BigDecimal.valueOf(99.99))
                         .quantity(100)
                         .description("New book description")
                         .build();
    }

    public static Customer testCustomer() {
        return Customer.builder()
                       .id(1L)
                       .name("New name")
                       .created_at(Date.from(LocalDate.parse("2024-10-02")
                                                      .atStartOfDay(ZoneId.systemDefault())
                                                      .toInstant()))
                       .build();
    }

    public static CustomerEntity testCustomerEntity() {
        return CustomerEntity.builder()
                             .id(1L)
                             .name("New name")
                             .created_at(Date.from(LocalDate.parse("2024-10-02")
                                                            .atStartOfDay(ZoneId.systemDefault())
                                                            .toInstant()))
                             .build();
    }
}