package com.example.bookstore.api.services;

import com.example.bookstore.api.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    boolean isOrderExists(Long id);

    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> listOrders();

    void deleteById(Long id);
}