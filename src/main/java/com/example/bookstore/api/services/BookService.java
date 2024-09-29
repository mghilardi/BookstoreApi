package com.example.bookstore.api.services;

import com.example.bookstore.api.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    boolean isBookExists(Long id);

    Book save(Book book);

    Optional<Book> findById(Long id);

    List<Book> listBooks();

    void deleteById(Long id);
}