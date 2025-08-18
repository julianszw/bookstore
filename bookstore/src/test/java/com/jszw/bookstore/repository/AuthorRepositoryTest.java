package com.jszw.bookstore.repository;

import com.jszw.bookstore.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired AuthorRepository repo;

    @Test
    void findByNameContainingIgnoreCase_ok() {
        repo.save(Author.builder().name("Robert C. Martin").build());
        repo.save(Author.builder().name("Martin Fowler").build());

        List<Author> out = repo.findByNameContainingIgnoreCase("martin");
        assertThat(out).extracting(Author::getName)
                .containsExactlyInAnyOrder("Robert C. Martin", "Martin Fowler");
    }

    @Test
    void search_jpql_ok() {
        repo.save(Author.builder().name("Erich Gamma").build());
        var out = repo.search("gamma");
        assertThat(out).singleElement().satisfies(a -> assertThat(a.getName()).isEqualTo("Erich Gamma"));
    }

    @Test
    void existsByNameIgnoreCase_ok() {
        repo.save(Author.builder().name("Kent Beck").build());
        assertThat(repo.existsByNameIgnoreCase("KENT BECK")).isTrue();
        assertThat(repo.existsByNameIgnoreCase("Unknown")).isFalse();
    }
}
