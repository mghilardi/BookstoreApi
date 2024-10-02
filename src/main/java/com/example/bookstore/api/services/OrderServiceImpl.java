package com.example.bookstore.api.services;

import com.example.bookstore.api.domain.Order;
import com.example.bookstore.api.entities.OrderEntity;
import com.example.bookstore.api.exception.ErrorMessages;
import com.example.bookstore.api.exception.ResourceNotFoundException;
import com.example.bookstore.api.repositories.OrderRepository;
import com.example.bookstore.api.services.mappers.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(final OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public boolean isOrderExists(Long id) {
        return orderRepository.existsById(id);
    }

    @Override
    public Order save(Order order) {
        final OrderEntity orderEntity = orderMapper.orderToOrderEntity(order);
        final OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        return orderMapper.orderEntityToOrder(savedOrderEntity);
    }

    @Override
    public Optional<Order> findById(Long id) {
        final Optional<OrderEntity> foundOrder = Optional.ofNullable(orderRepository.findById(id)
                                                                                    .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.NOT_EXIST_WITH_ID + id)));
        Order order = orderMapper.orderEntityToOrder(foundOrder.get());
        return Optional.of(order);
    }

    @Override
    public List<Order> listOrders() {
        final List<OrderEntity> foundOrders = orderRepository.findAll();
        return foundOrders.stream()
                          .map(orderMapper::orderEntityToOrder)
                          .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}