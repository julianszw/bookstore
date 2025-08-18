package com.jszw.bookstore.repository;

import com.jszw.bookstore.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class BookEditionRepositoryTest {

    @Autowired BookEditionRepository editions;
    @Autowired BookRepository books;
    @Autowired AuthorRepository authors;
    @Autowired PublisherRepository publishers;

    @Test
    void findAllByBookIdOrderByPublishedYearAsc_ok() {
        var a = authors.save(Author.builder().name("Autor").build());
        var p = publishers.save(Publisher.builder().name("Pub").build());
        var b = books.save(Book.builder().title("Libro").author(a).build());

        editions.save(BookEdition.builder().book(b).publisher(p).publishedYear(2021).build());
        editions.save(BookEdition.builder().book(b).publisher(p).publishedYear(2019).build());

        var out = editions.findAllByBookIdOrderByPublishedYearAsc(b.getId());
        assertThat(out).extracting(BookEdition::getPublishedYear).containsExactly(2019, 2021);
        // Verificamos que el publisher venga cargado por EntityGraph
        assertThat(out.get(0).getPublisher()).isNotNull();
    }

    @Test
    void findAllByPublisherId_ok() {
        var a = authors.save(Author.builder().name("Autor").build());
        var p = publishers.save(Publisher.builder().name("Pub").build());
        var b = books.save(Book.builder().title("Libro").author(a).build());
        editions.save(BookEdition.builder().book(b).publisher(p).publishedYear(2020).build());

        var out = editions.findAllByPublisherId(p.getId());
        assertThat(out).hasSize(1);
        // Verificamos que el book venga cargado por EntityGraph
        assertThat(out.get(0).getBook()).isNotNull();
    }

    @Test
    void countAndDeleteByBookId_ok() {
        var a = authors.save(Author.builder().name("Autor").build());
        var p = publishers.save(Publisher.builder().name("Pub").build());
        var b = books.save(Book.builder().title("Libro").author(a).build());

        editions.save(BookEdition.builder().book(b).publisher(p).publishedYear(2020).build());
        editions.save(BookEdition.builder().book(b).publisher(p).publishedYear(2021).build());

        assertThat(editions.countByBookId(b.getId())).isEqualTo(2);
        assertThat(editions.deleteByBookId(b.getId())).isEqualTo(2);
        assertThat(editions.countByBookId(b.getId())).isZero();
    }
}
