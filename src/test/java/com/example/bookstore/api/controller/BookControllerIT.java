package com.example.bookstore.api.controller;

import com.example.bookstore.api.domain.Book;
import com.example.bookstore.api.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.example.bookstore.api.stub.StubData.testBook;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createBook_returns201() throws Exception {
        final Book book = testBook();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/books")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(bookJson))
                .andExpect(MockMvcResultMatchers.status()
                                                .isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
//                                                .value(book.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn")
                                                .value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author")
                                                .value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title")
                                                .value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price")
                                                .value(book.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity")
                                                .value(book.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                                                .value(book.getDescription()));

    }

    @Test
    void updateBook_returns200() throws Exception {
        final Book book = testBook();
        Book saved = bookService.save(book);

        book.setAuthor("New Author Y");

        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);

        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/books/" + saved.getId())
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(bookJson))
                .andExpect(MockMvcResultMatchers.status()
                                                .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn")
                                                .value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author")
                                                .value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title")
                                                .value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price")
                                                .value(book.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity")
                                                .value(book.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                                                .value(book.getDescription()));
    }

    @Test
    void getBook_returns404WhenBookNotFound() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/books/" + 0))
                .andExpect(MockMvcResultMatchers.status()
                                                .isNotFound());
    }

    @Test
    void getBook_returns200WhenBookExists() throws Exception {
        final Book book = testBook();
        Book saved = bookService.save(book);
        mockMvc
                .perform(MockMvcRequestBuilders.get("/books/" + saved.getId()))
                .andExpect(MockMvcResultMatchers.status()
                                                .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn")
                                                .value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author")
                                                .value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title")
                                                .value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price")
                                                .value(book.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity")
                                                .value(book.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                                                .value(book.getDescription()));
    }

    @Test
    void listBooks_returnsHttp200EmptyListWhenNoBooksExist() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status()
                                                .isOk())
                .andExpect(MockMvcResultMatchers.content()
                                                .string("[]"));
    }

    @Test
    void listBooks_returnsHttp200AndBooksWhenBooksExist() throws Exception {
        final Book book = testBook();
        bookService.save(book);
        mockMvc
                .perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status()
                                                .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].isbn")
                                                .value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].author")
                                                .value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title")
                                                .value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].price")
                                                .value(book.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].quantity")
                                                .value(book.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].description")
                                                .value(book.getDescription()));

    }

    @Test
    void deleteBook_returns204WhenBookExists() throws Exception {
        final Book book = testBook();
        Book saved = bookService.save(book);

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/books/" + saved.getId()))
                .andExpect(MockMvcResultMatchers.status()
                                                .isNoContent());
    }

    @Test
    void deleteBook_returns204WhenBookDoesntExist() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/books/" + 0L))
                .andExpect(MockMvcResultMatchers.status()
                                                .isNoContent());
    }
}