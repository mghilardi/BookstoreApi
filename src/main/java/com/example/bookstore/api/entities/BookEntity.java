package com.example.bookstore.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "isbn is required")
    @Size(min = 5, max = 13, message = "Invalid isbn: Must be of 5 - 13 characters")
    @Column(name = "isbn")
    private String isbn;

    @NotNull(message = "title is required")
    private String title;

    @NotNull(message = "author is required")
    private String author;

    private BigDecimal price;
    private int quantity;
    private String description;

    @ManyToMany(mappedBy = "books")
    List<OrderEntity> orders = new ArrayList<>();
    
}
