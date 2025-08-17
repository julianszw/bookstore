package com.jszw.bookstore.repository;

import com.jszw.bookstore.domain.BookEdition;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookEditionRepository extends JpaRepository<BookEdition, Long> {

    /** Todas las ediciones de un libro, ordenadas por número de edición (fetch del publisher para evitar N+1). */
    @EntityGraph(attributePaths = {"publisher"})
    List<BookEdition> findAllByBookIdOrderByEditionNumberAsc(Long bookId);

    /** Buscar una edición específica por (bookId, editionNumber). Útil para validar unicidad. */
    Optional<BookEdition> findByBookIdAndEditionNumber(Long bookId, Integer editionNumber);

    /** Chequear si existe una edición concreta de un libro. */
    boolean existsByBookIdAndEditionNumber(Long bookId, Integer editionNumber);

    /** Todas las ediciones de una editorial (fetch del book para evitar N+1). */
    @EntityGraph(attributePaths = {"book"})
    List<BookEdition> findAllByPublisherId(Long publisherId);

    /** Borrado masivo de ediciones por libro (devuelve cuántas filas borró). */
    long deleteByBookId(Long bookId);

    /** Cantidad de ediciones que tiene un libro. */
    long countByBookId(Long bookId);
}
