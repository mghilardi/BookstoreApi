package com.example.bookstore.api.controller;

import com.example.bookstore.api.domain.Book;
import com.example.bookstore.api.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@Validated
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody final Book book) {
        final Book savedBook = bookService.save(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable final Long id, @Valid @RequestBody final Book bookDetails) {

        Optional<Book> bookFound = bookService.findById(id);
        Book book = bookFound.get();
        book.setIsbn(bookDetails.getIsbn());
        book.setAuthor(bookDetails.getAuthor());
        book.setDescription(bookDetails.getDescription());
        book.setTitle(bookDetails.getTitle());
        book.setPrice(bookDetails.getPrice());
        book.setQuantity(bookDetails.getQuantity());

        final Book savedBook = bookService.save(book);
        return new ResponseEntity<>(savedBook, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Book> getBook(@PathVariable final Long id) {
        final Optional<Book> foundBook = bookService.findById(id);
        return foundBook.map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Book>> listBooks() {
        return new ResponseEntity<>(bookService.listBooks(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteBook(@PathVariable final Long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
