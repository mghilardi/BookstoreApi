package com.example.bookstore.api.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(NON_NULL)
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
}
