package com.jszw.bookstore.repository;

import com.jszw.bookstore.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired UserRepository repo;

    @Test
    void findByUsername_ok() {
        repo.save(User.builder().username("jsz").email("j@x.com").password("pw").build());
        var found = repo.findByUsername("jsz");
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("j@x.com");
    }
}
