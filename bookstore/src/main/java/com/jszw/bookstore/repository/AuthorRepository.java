package com.jszw.bookstore.repository;

import com.jszw.bookstore.domain.Author;
import com.jszw.bookstore.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findById(Long id);
    List<Author> findByFirstNameOrLastNameContainingIgnoreCase(String keyword1, String keyword2);

}
