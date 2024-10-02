package com.example.bookstore.api.services;

import com.example.bookstore.api.domain.Book;
import com.example.bookstore.api.entities.BookEntity;
import com.example.bookstore.api.exception.ErrorMessages;
import com.example.bookstore.api.exception.ResourceNotFoundException;
import com.example.bookstore.api.repositories.BookRepository;
import com.example.bookstore.api.services.mappers.BookMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(final BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Book save(final Book book) {
        final BookEntity bookEntity = bookMapper.bookToBookEntity(book);
        final BookEntity savedBookEntity = bookRepository.save(bookEntity);
        return bookMapper.bookEntityToBook(savedBookEntity);
    }

    @Override
    public Optional<Book> findById(Long id) {
        final Optional<BookEntity> foundBook = Optional.ofNullable(bookRepository.findById(id)
                                                                                 .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.NOT_EXIST_WITH_ID + id)));
        Book book = bookMapper.bookEntityToBook(foundBook.get());
        return Optional.of(book);
    }


    @Override
    public List<Book> listBooks() {
        final List<BookEntity> foundBooks = bookRepository.findAll(Sort.by(Sort.Direction.ASC, "title")
                                                                       .and(Sort.by(Sort.Direction.ASC, "author")));
        return foundBooks.stream()
                         .map(bookMapper::bookEntityToBook)
                         .collect(Collectors.toList());
    }

    @Override
    public boolean isBookExists(Long id) {
        return bookRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

}

















