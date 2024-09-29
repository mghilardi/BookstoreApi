package com.example.bookstore.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private BigDecimal price;
    private int quantity;
    private String description;
}
