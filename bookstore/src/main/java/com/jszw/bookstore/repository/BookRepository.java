package com.jszw.bookstore.repository;

import com.jszw.bookstore.domain.Book;
import com.jszw.bookstore.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);
    Optional<Book> findByTitle(String title);
    List<Book> findByTitleContainingIgnoreCase(String keyword);
    List<Book> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Book> findByAuthorId(Long authorId);
    List<Book> findByAuthorIdOrderByTitleAsc(Long authorId);
    List<Book> findByPriceLessThan(Double price);
    List<Book> findByPriceGreaterThan(Double price);
    List<Book> findByAuthorIdAndPriceLessThan(Long authorId, Double price);
    List<Book> findByCategoryNameIgnoreCase(String categoryName);
}
