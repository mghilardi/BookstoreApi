package com.example.bookstore.api.services;

import com.example.bookstore.api.domain.Book;
import com.example.bookstore.api.entities.BookEntity;
import com.example.bookstore.api.exception.ResourceNotFoundException;
import com.example.bookstore.api.repositories.BookRepository;
import com.example.bookstore.api.services.mappers.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static com.example.bookstore.api.stub.StubData.testBook;
import static com.example.bookstore.api.stub.StubData.testBookEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl underTest;

    @BeforeEach
    void setUp() {
    }

    @Test
    void save() {
        final Book book = testBook();
        final BookEntity bookEntity = testBookEntity();

        when(bookMapper.bookToBookEntity(eq(book))).thenCallRealMethod();
        when(bookRepository.save(eq(bookEntity))).thenReturn(bookEntity);
        when(bookMapper.bookEntityToBook(eq(bookEntity))).thenCallRealMethod();

        final Book result = underTest.save(book);
        assertEquals(book, result);
    }

    @Test
    void findById_throwsResourceNotFoundException() {
        final Long id = 1L;
        when(bookRepository.findById(eq(id))).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            underTest.findById(id);
        });
        assertEquals("Resource does not exist with id: 1", exception.getMessage());
    }

    @Test
    void findById_returnsBookWhenExists() {
        final Book book = testBook();
        final BookEntity bookEntity = testBookEntity();

        when(bookRepository.findById(eq(book.getId()))).thenReturn(Optional.of(bookEntity));
        when(bookMapper.bookEntityToBook(eq(bookEntity))).thenCallRealMethod();

        final Optional<Book> result = underTest.findById(book.getId());
        assertEquals(Optional.of(book), result);
    }

    @Test
    void listBooks_returnsEmptyListWhenNoBooksExist() {
        final BookEntity bookEntity = testBookEntity();
        when(bookRepository.findAll(any(Sort.class))).thenReturn(List.of(bookEntity));
        final List<Book> result = underTest.listBooks();
        assertEquals(1, result.size());
    }

    @Test
    void listBooks_returnsBooksWhenExist() {
        final BookEntity bookEntity = testBookEntity();
        when(bookRepository.findAll(any(Sort.class))).thenReturn(List.of(bookEntity));
        final List<Book> result = underTest.listBooks();
        assertEquals(1, result.size());
    }

    @Test
    void isBookExists_returnsFalseWhenBookDoesntExist() {
        when(bookRepository.existsById(any())).thenReturn(false);
        final boolean result = underTest.isBookExists(testBook().getId());
        assertFalse(result);
    }

    @Test
    void deleteById() {
        final Long id = 1L;
        underTest.deleteById(id);
        verify(bookRepository, times(1)).deleteById(eq(id));
    }

}
