package com.Netcracker.Assignment.service;

import com.Netcracker.Assignment.model.Books;
import com.Netcracker.Assignment.repository.BookRepository;
import com.Netcracker.Assignment.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    public List<Books> getAllBooks() {
        List<Books> books = bookRepository.findAll();
        for (Books book : books) {
            book.setAuthor(null); // Avoid redundant author data
        }
        return books;
    }

    public Books getBookById(Long id) {
        Books book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.getAuthor().setBooks(null); // Avoid redundancy
        }
        return book;
    }

    public List<Books> getBooksByAuthor(Long authorId) {
        List<Books> books = bookRepository.findByAuthorId(authorId);
        for (Books book : books) {
            book.setAuthor(null); // Avoid redundant author data
        }
        return books;
    }

    public Books addBook(Books book) {
        Books savedBook = bookRepository.save(book);
        kafkaProducer.sendMessage("Book added: " + savedBook.getTitle());
        savedBook.setAuthor(null); // Avoid redundant author data
        return savedBook;
    }

    public Books updateBook(Long id, Books bookDetails) {
        Books book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setTitle(bookDetails.getTitle());
            book.setPublishedDate(bookDetails.getPublishedDate());
            book.setAuthor(bookDetails.getAuthor());
            Books updatedBook = bookRepository.save(book);
            kafkaProducer.sendMessage("Book updated: " + updatedBook.getTitle());
            updatedBook.setAuthor(null); // Avoid redundant author data
            return updatedBook;
        } else {
            return null;
        }
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
        kafkaProducer.sendMessage("Book deleted with ID: " + id);
    }
}
