package com.jszw.bookstore.repository;

import com.jszw.bookstore.domain.Author;
import com.jszw.bookstore.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired BookRepository books;
    @Autowired AuthorRepository authors;

    @Test
    void findByIsbn_ok() {
        var a = authors.save(Author.builder().name("Autor").build());
        books.save(Book.builder().title("Libro").isbn("ISBN-123").author(a).build());

        var out = books.findByIsbn("ISBN-123");
        assertThat(out).isPresent();
        assertThat(out.get().getTitle()).isEqualTo("Libro");
    }

    @Test
    void findByAuthorId_ok() {
        var a1 = authors.save(Author.builder().name("A1").build());
        var a2 = authors.save(Author.builder().name("A2").build());
        books.save(Book.builder().title("X").author(a1).build());
        books.save(Book.builder().title("Y").author(a1).build());
        books.save(Book.builder().title("Z").author(a2).build());

        var out = books.findByAuthorId(a1.getId());
        assertThat(out).extracting(Book::getTitle).containsExactlyInAnyOrder("X","Y");
    }

    @Test
    void search_native_ilike_ok() {
        var a = authors.save(Author.builder().name("Autor").build());
        books.save(Book.builder().title("Clean Code").isbn("111").description("Prácticas")
                .author(a).build());
        books.save(Book.builder().title("Algo").isbn("222").description("Nada").author(a).build());

        var out = books.search("clean");
        assertThat(out).extracting(Book::getIsbn).contains("111");
    }
}
