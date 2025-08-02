package com.jszw.bookstore.repository;

import com.jszw.bookstore.domain.BookEdition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookEditionRepository extends JpaRepository<BookEdition, Long> {}