package com.Netcracker.Assignment.service;

import com.Netcracker.Assignment.model.Authors;
import com.Netcracker.Assignment.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Netcracker.Assignment.kafka.*;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    public List<Authors> getAllAuthors() {
        return authorRepository.findAll();
    }

    // AuthorService.java
    public Authors getAuthorById(Long authorId) {
        return authorRepository.findById(authorId).orElseThrow(() -> new IllegalArgumentException("Author not found"));
    }


    public Authors addAuthor(Authors authors) {
        Authors savedAuthors = authorRepository.save(authors);
        kafkaProducer.sendMessage("Author added: " + savedAuthors.getName());
        return savedAuthors;
    }

    public Authors updateAuthor(Long id, Authors authorsDetails) {
        Authors authors = authorRepository.findById(id).orElse(null);
        if (authors != null) {
            authors.setName(authorsDetails.getName());
            Authors updatedAuthors = authorRepository.save(authors);
            kafkaProducer.sendMessage("Author updated: " + updatedAuthors.getName());
            return updatedAuthors;
        } else {
            return null;
        }
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
        kafkaProducer.sendMessage("Author deleted with ID: " + id);
    }
}
