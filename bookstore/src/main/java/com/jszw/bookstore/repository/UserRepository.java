package com.jszw.bookstore.repository;

import com.jszw.bookstore.domain.Book;
import com.jszw.bookstore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}


