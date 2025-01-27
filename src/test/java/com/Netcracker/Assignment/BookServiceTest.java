package com.Netcracker.Assignment;

import com.Netcracker.Assignment.kafka.KafkaProducer;
import com.Netcracker.Assignment.model.Books;
import com.Netcracker.Assignment.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.Netcracker.Assignment.service.BookService;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private BookService bookService;

    public BookServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddBook() {
        Books books = new Books();
        books.setId(1L);
        books.setTitle("Test Book");
        books.setPublishedDate(LocalDate.now());

        when(bookRepository.save(any(Books.class))).thenReturn(books);

        Books savedBook = bookService.addBook(books);

        assertNotNull(savedBook);
        assertEquals("Test Book", savedBook.getTitle());
        verify(kafkaProducer, times(1)).sendMessage("Book added: Test Book");
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;

        doNothing().when(bookRepository).deleteById(bookId);

        bookService.deleteBook(bookId);

        verify(bookRepository, times(1)).deleteById(bookId);
        verify(kafkaProducer, times(1)).sendMessage("Book deleted with ID: 1");
    }
}
