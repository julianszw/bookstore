package com.jszw.bookstore.repository;

import com.jszw.bookstore.domain.Category;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired CategoryRepository repo;

    @Test
    void existsByNameIgnoreCase_ok() {
        repo.save(Category.builder().name("Sci-Fi").build());
        assertThat(repo.existsByNameIgnoreCase("sci-fi")).isTrue();
        assertThat(repo.existsByNameIgnoreCase("Drama")).isFalse();
    }

    // Descomenta si el campo name es unique en la entidad:
    // @Test
    void unique_name_enforced() {
        repo.save(Category.builder().name("TDD").build());
        repo.save(Category.builder().name("TDD").build());
        assertThatThrownBy(() -> repo.flush()).isInstanceOf(PersistenceException.class);
    }
}
