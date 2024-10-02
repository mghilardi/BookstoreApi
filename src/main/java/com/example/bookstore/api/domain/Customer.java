package com.example.bookstore.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(NON_DEFAULT)

public class Customer {

    private Long id;
    @NotBlank(message = "The name is mandatory")
    private String name;
    private Date created_at;
    private List<Order> orders;

    public Customer(Long id) {
        this.id = id;
    }
}
