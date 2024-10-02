package com.example.bookstore.api.controller;

import com.example.bookstore.api.domain.Order;
import com.example.bookstore.api.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody final Order order) {
        final Order savedOrder = orderService.save(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable final Long id,
                                             @Valid @RequestBody final Order orderDetails) {

        Optional<Order> orderFound = orderService.findById(id);
        Order order = orderFound.get();
        order.setBooks(orderDetails.getBooks());

        final Order savedOrder = orderService.save(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable final Long id) {
        final Optional<Order> foundOrder = orderService.findById(id);
        return foundOrder.map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                         .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Order>> listOrders() {
        return new ResponseEntity<>(orderService.listOrders(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteOrder(@PathVariable final Long id) {
        orderService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
