package com.example.bookstore.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(NON_DEFAULT)

public class Book {
    private Long id;
    @NotBlank(message = "The ISBN is mandatory")
    @Size(min = 5, max = 13, message = "The ISBN must be of 5 - 13 characters")
    private String isbn;
    @NotBlank(message = "The title is mandatory")
    private String title;
    @NotBlank(message = "The author is mandatory")
    private String author;
    @NotNull(message = "The price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "The price must be greater than zero")
    @Digits(integer = 9, fraction = 2, message = "The price must have no more than 2 fraction digits")
    private BigDecimal price;
    private int quantity;
    private String description;

    public Book(Long id) {
        this.id = id;
    }

}
