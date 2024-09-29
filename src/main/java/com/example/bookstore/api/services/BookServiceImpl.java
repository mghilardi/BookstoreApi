package com.example.bookstore.api.services;

import com.example.bookstore.api.domain.Book;
import com.example.bookstore.api.entities.BookEntity;
import com.example.bookstore.api.exception.ErrorMessages;
import com.example.bookstore.api.exception.ResourceNotFoundException;
import com.example.bookstore.api.repositories.BookRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Book save(final Book book) {
        final BookEntity bookEntity = bookToBookEntity(book);
        final BookEntity savedBookEntity = bookRepository.save(bookEntity);
        return bookEntityToBook(savedBookEntity);
    }

    @Override
    public Optional<Book> findById(Long id) {
        final Optional<BookEntity> foundBook = Optional.ofNullable(bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_BOOK_NOT_FOUND + id)));
        return foundBook.map(this::bookEntityToBook);
    }


    @Override
    public List<Book> listBooks() {
        final List<BookEntity> foundBooks = bookRepository.findAll();
        return foundBooks.stream().map(this::bookEntityToBook).collect(Collectors.toList());
    }

    @Override
    public boolean isBookExists(Long id) {
        return bookRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        try {
            bookRepository.deleteById(id);

        } catch (final EmptyResultDataAccessException ex) {
            log.debug("Attempted to delete non-existing book", ex);
        }
    }

    public BookEntity bookToBookEntity(Book book) {
        return BookEntity.builder().id(book.getId()).isbn(book.getIsbn()).title(book.getTitle()).author(book.getAuthor()).price(book.getPrice()).quantity(book.getQuantity()).description(book.getDescription()).build();
    }

    public Book bookEntityToBook(BookEntity bookEntity) {
        return Book.builder().id(bookEntity.getId()).isbn(bookEntity.getIsbn()).title(bookEntity.getTitle()).author(bookEntity.getAuthor()).price(bookEntity.getPrice()).quantity(bookEntity.getQuantity()).description(bookEntity.getDescription()).build();
    }
}

















