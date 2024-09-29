package com.example.bookstore.api.controller;

//public class ContactResource {
//    private final ContactService contactService;
//
//    @PostMapping
//    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
//        //return ResponseEntity.ok().body(contactService.createContact(contact));
//        return ResponseEntity.created(URI.create("/contacts/userID")).body(contactService.createContact(contact));
//    }
//
//    @GetMapping
//    public ResponseEntity<Page<Contact>> getContacts(@RequestParam(value = "page", defaultValue = "0") int page,
//                                                     @RequestParam(value = "size", defaultValue = "10") int size) {
//        return ResponseEntity.ok().body(contactService.getAllContacts(page, size));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Contact> getContact(@PathVariable(value = "id") String id) {
//        return ResponseEntity.ok().body(contactService.getContact(id));
//    }
//
//    @PutMapping("/photo")
//    public ResponseEntity<String> uploadPhoto(@RequestParam("id") String id, @RequestParam("file") MultipartFile file) {
//        return ResponseEntity.ok().body(contactService.uploadPhoto(id, file));
//    }
//
//
//    @GetMapping(path = "/image/{filename}", produces = {IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE})
//    public byte[] getPhoto(@PathVariable("filename") String filename) throws IOException {
//        return Files.readAllBytes(Paths.get(PHOTO_DIRECTORY + filename));
//    }
//}


import com.example.bookstore.api.domain.Book;
import com.example.bookstore.api.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody final Book book) {
        return saveBook(book.getId(), book);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable final Long id, @Valid @RequestBody final Book book) {
        return saveBook(id, book);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Book> getBook(@PathVariable final Long id) {
        final Optional<Book> foundBook = bookService.findById(id);
        return foundBook.map(book -> new ResponseEntity<>(book, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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

    private ResponseEntity<Book> saveBook(Long id, Book book) {
        book.setId(id);

        final boolean isBookExists = bookService.isBookExists(id);
        final Book savedBook = bookService.save(book);

        if (isBookExists) {
            return new ResponseEntity<>(savedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        }
    }

}
