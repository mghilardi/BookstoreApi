package com.example.bookstore.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "customer_id", referencedColumnName = "id")
//    private CustomerEntity customer;
//
//    @ManyToMany
//    @JoinTable(
//            name = "order_book",
//            joinColumns = @JoinColumn(name = "order_id"),
//            inverseJoinColumns = @JoinColumn(name = "book_id"))
//    private List<BookEntity> books;


    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToMany
    @JoinTable(
            name = "order_book",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<BookEntity> books = new ArrayList<>();


    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date created_at;


//    public List<Long> getBooks() {
//        List<Long> bookIds = new ArrayList<>();
//        for (BookEntity b : books) {
//            bookIds.add(b.getId());
//        }
//        return bookIds;
//    }
//
//    public void setBooks(List<BookEntity> books) {
//        this.books = books;
//    }
//
//    public String getCustomer() {
//        return customer.getId();
//    }
//
//    public void setCustomer(CustomerEntity customer) {
//        this.customer = customer;
//    }
}