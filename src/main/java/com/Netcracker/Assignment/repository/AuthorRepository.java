package com.Netcracker.Assignment.repository;

import com.Netcracker.Assignment.model.Authors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// AuthorRepository.java
@Repository
public interface AuthorRepository extends JpaRepository<Authors, Long> {
    // Custom query methods (if needed)
}

