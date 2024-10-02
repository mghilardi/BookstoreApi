package com.example.bookstore.api.services.mappers;

import com.example.bookstore.api.domain.Order;
import com.example.bookstore.api.entities.OrderEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Slf4j
public class OrderMapper {

    private final CustomerMapper customerMapper;
    private final BookMapper bookMapper;

    @Autowired
    public OrderMapper(CustomerMapper customerMapper, BookMapper bookMapper) {
        this.customerMapper = customerMapper;
        this.bookMapper = bookMapper;
    }

    public OrderEntity orderToOrderEntity(Order order) {
        return OrderEntity.builder()
                          .id(order.getId())
                          .customer(customerMapper.customerToCustomerEntity(order.getCustomer()))
                          .created_at(order.getCreated_at())
                          .books(order.getBooks()
                                      .stream()
                                      .map(
                                              bookMapper::bookToBookEntity
                                      )
                                      .collect(Collectors.toList()))
                          .build();
    }

    public Order orderEntityToOrder(OrderEntity orderEntity) {
        return Order.builder()
                    .id(orderEntity.getId())
                    .customer(customerMapper.customerEntityToCustomer(orderEntity.getCustomer()))
                    .created_at(orderEntity.getCreated_at())
                    .books(orderEntity.getBooks()
                                      .stream()
                                      .map(
                                              bookMapper::bookEntityToBook
                                      )
                                      .collect(Collectors.toList()))
                    .build();
    }
}
