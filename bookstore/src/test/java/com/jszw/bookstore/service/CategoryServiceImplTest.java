package com.jszw.bookstore.service;

import com.jszw.bookstore.domain.Category;
import com.jszw.bookstore.dto.requestDto.CategoryRequestDTO;
import com.jszw.bookstore.dto.responseDto.CategoryResponseDTO;
import com.jszw.bookstore.exception.ResourceNotFoundException;
import com.jszw.bookstore.mapper.CategoryMapper;
import com.jszw.bookstore.repository.CategoryRepository;
import com.jszw.bookstore.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock CategoryRepository repository;
    @Mock CategoryMapper mapper;
    @InjectMocks CategoryServiceImpl service;

    Category entity;
    CategoryResponseDTO dtoRes;

    @BeforeEach
    void init() {
        entity = new Category();
        entity.setId(5L);
        entity.setName("Sci-Fi");

        dtoRes = CategoryResponseDTO.builder()
                .id(5L)
                .name("Sci-Fi")
                .build();
    }

    @Test
    void getCategories_ok() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDto(entity)).thenReturn(dtoRes);

        var out = service.getCategories();

        assertThat(out).hasSize(1);
        assertThat(out.get(0).getName()).isEqualTo("Sci-Fi");
    }

    @Test
    void findById_found() {
        when(repository.findById(5L)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dtoRes);

        var out = service.findById(5L);
        assertThat(out.getId()).isEqualTo(5L);
    }

    @Test
    void findById_notFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_ok() {
        when(repository.save(any(Category.class))).thenAnswer(inv -> {
            Category c = inv.getArgument(0);
            c.setId(10L); return c;
        });
        when(mapper.toDto(any(Category.class))).thenReturn(dtoRes);

        var req = new CategoryRequestDTO();
        req.setName("Sci-Fi");

        var out = service.create(req);

        verify(repository).save(any(Category.class));
        assertThat(out.getName()).isEqualTo("Sci-Fi");
    }

    @Test
    void update_ok_sets_fields_and_saves() {
        when(repository.findById(5L)).thenReturn(Optional.of(entity));
        when(repository.save(any(Category.class))).thenAnswer(inv -> inv.getArgument(0));
        when(mapper.toDto(any(Category.class))).thenReturn(dtoRes);

        var upd = new CategoryRequestDTO();
        upd.setName("Updated");

        ArgumentCaptor<Category> cap = ArgumentCaptor.forClass(Category.class);

        var out = service.update(5L, upd);

        verify(repository).save(cap.capture());
        assertThat(cap.getValue().getName()).isEqualTo("Updated");
        assertThat(out).isNotNull();
    }

    @Test
    void deleteById_ok() {
        when(repository.findById(5L)).thenReturn(Optional.of(entity));
        service.deleteById(5L);
        verify(repository).delete(entity);
    }
}
