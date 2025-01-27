package com.Netcracker.Assignment.repository;

import com.Netcracker.Assignment.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Books, Long> {
    List<Books> findByAuthorId(Long authorId);
}
