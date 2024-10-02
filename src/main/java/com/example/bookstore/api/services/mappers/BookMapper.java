package com.example.bookstore.api.services.mappers;

import com.example.bookstore.api.domain.Book;
import com.example.bookstore.api.entities.BookEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookMapper {
    public BookEntity bookToBookEntity(Book book) {
        return BookEntity.builder()
                         .id(book.getId())
                         .isbn(book.getIsbn())
                         .title(book.getTitle())
                         .author(book.getAuthor())
                         .price(book.getPrice())
                         .quantity(book.getQuantity())
                         .description(book.getDescription())
                         .build();
    }

    public Book bookEntityToBook(BookEntity bookEntity) {
        return Book.builder()
                   .id(bookEntity.getId())
                   .isbn(bookEntity.getIsbn())
                   .title(bookEntity.getTitle())
                   .author(bookEntity.getAuthor())
                   .price(bookEntity.getPrice())
                   .quantity(bookEntity.getQuantity())
                   .description(bookEntity.getDescription())
                   .build();
    }
}
