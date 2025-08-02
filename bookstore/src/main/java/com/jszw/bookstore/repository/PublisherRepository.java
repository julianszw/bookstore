package com.jszw.bookstore.repository;

import com.jszw.bookstore.domain.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {}