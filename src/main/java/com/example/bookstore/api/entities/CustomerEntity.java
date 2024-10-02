package com.example.bookstore.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "title is required")
    private String name;

//    @OneToMany
//    @JoinColumn(name = "customer_id", referencedColumnName = "id")
//    private List<OrderEntity> orders;

    @OneToMany(mappedBy = "customer")
    private List<OrderEntity> orders = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date created_at;

//    public List<OrderEntity> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<OrderEntity> orders) {
//        this.orders = orders;
//    }

}