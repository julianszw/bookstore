package com.jszw.bookstore.repository;

import com.jszw.bookstore.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    // Búsquedas seguras (sin tocar CLOB con lower())
    List<Book> findByAuthorId(Long authorId);

    // Evitamos usar "description" en métodos derivados con IgnoreCase para no forzar lower() sobre CLOB
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Por categoría
    List<Book> findByCategories_NameIgnoreCase(String categoryName);

    boolean existsByIsbn(String isbn);

    /**
     * Búsqueda genérica: usa SQL nativo con ILIKE (PostgreSQL) y castea description (CLOB) a text.
     * DISTINCT para evitar duplicados si hay joins futuros.
     */
    @Query(value = """
            SELECT DISTINCT b.*
            FROM book b
            WHERE b.title ILIKE CONCAT('%', :kw, '%')
               OR CAST(b.description AS text) ILIKE CONCAT('%', :kw, '%')
               OR b.isbn ILIKE CONCAT('%', :kw, '%')
            """,
            nativeQuery = true)
    List<Book> search(@Param("kw") String keyword);
}
