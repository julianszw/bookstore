package com.jszw.bookstore.repository;

import com.jszw.bookstore.domain.BookEdition;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookEditionRepository extends JpaRepository<BookEdition, Long> {

    /** Todas las ediciones de un libro, ordenadas por año (fetch del publisher). */
    @EntityGraph(attributePaths = {"publisher"})
    List<BookEdition> findAllByBookIdOrderByPublishedYearAsc(Long bookId);

    /** Todas las ediciones de una editorial (fetch del book). */
    @EntityGraph(attributePaths = {"book"})
    List<BookEdition> findAllByPublisherId(Long publisherId);

    /** Borrado masivo por libro. */
    long deleteByBookId(Long bookId);

    /** Cantidad de ediciones que tiene un libro. */
    long countByBookId(Long bookId);

    /** Para evitar duplicados por ISBN-13. */
    boolean existsByIsbn13(String isbn13);
}
