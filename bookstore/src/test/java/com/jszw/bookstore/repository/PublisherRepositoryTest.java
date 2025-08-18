package com.jszw.bookstore.repository;

import com.jszw.bookstore.domain.Publisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PublisherRepositoryTest {

    @Autowired PublisherRepository repo;

    @Test
    void save_and_find_ok() {
        var p = repo.save(Publisher.builder().name("Addison-Wesley").build());
        var found = repo.findById(p.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Addison-Wesley");
    }
}
