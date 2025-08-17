package com.jszw.bookstore.repository;

import com.jszw.bookstore.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    // Búsqueda simple por el único nombre
    List<Author> findByNameContainingIgnoreCase(String name);

    // Alternativa equivalente con JPQL (por si preferís un único método "search")
    @Query("select a from Author a where lower(a.name) like lower(concat('%', ?1, '%'))")
    List<Author> search(String keyword);

    boolean existsByNameIgnoreCase(String name);
}
