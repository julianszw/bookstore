package com.jszw.bookstore.service;

import com.jszw.bookstore.domain.Author;
import com.jszw.bookstore.dto.requestDto.AuthorRequestDTO;
import com.jszw.bookstore.dto.responseDto.AuthorResponseDTO;
import com.jszw.bookstore.exception.ResourceNotFoundException;
import com.jszw.bookstore.mapper.AuthorMapper;
import com.jszw.bookstore.repository.AuthorRepository;
import com.jszw.bookstore.service.impl.AuthorServiceImpl;
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
class AuthorServiceImplTest {

    @Mock AuthorRepository repository;
    @Mock AuthorMapper mapper;
    @InjectMocks AuthorServiceImpl service;

    Author entity;
    AuthorResponseDTO dtoRes;

    @BeforeEach
    void init() {
        entity = new Author();
        entity.setId(1L);
        entity.setName("Kent Beck");

        dtoRes = AuthorResponseDTO.builder()
                .id(1L)
                .name("Kent Beck")
                .build();
    }

    @Test
    void getAuthors_ok() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDto(entity)).thenReturn(dtoRes);

        var out = service.getAuthors();

        assertThat(out).hasSize(1);
        assertThat(out.get(0).getName()).isEqualTo("Kent Beck");
    }

    @Test
    void findById_found() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dtoRes);

        var out = service.findById(1L);

        assertThat(out.getId()).isEqualTo(1L);
    }

    @Test
    void findById_notFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_ok() {
        when(repository.save(any(Author.class))).thenAnswer(inv -> {
            Author a = inv.getArgument(0);
            a.setId(10L); return a;
        });
        when(mapper.toDto(any(Author.class))).thenReturn(dtoRes);

        var req = new AuthorRequestDTO();
        req.setName("Kent Beck");

        var out = service.create(req);

        verify(repository).save(any(Author.class));
        assertThat(out.getName()).isEqualTo("Kent Beck");
    }

    @Test
    void update_ok_sets_fields_and_saves() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any(Author.class))).thenAnswer(inv -> inv.getArgument(0));
        when(mapper.toDto(any(Author.class))).thenReturn(dtoRes);

        var upd = new AuthorRequestDTO();
        upd.setName("Updated");

        ArgumentCaptor<Author> cap = ArgumentCaptor.forClass(Author.class);

        var out = service.update(1L, upd);

        verify(repository).save(cap.capture());
        assertThat(cap.getValue().getName()).isEqualTo("Updated");
        assertThat(out.getName()).isEqualTo("Kent Beck"); // viene del stub de mapper
    }

    @Test
    void patch_ok_only_non_null() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDto(any(Author.class))).thenReturn(dtoRes);

        var patch = new AuthorRequestDTO();
        patch.setName("Only name");

        var out = service.patch(1L, patch);

        assertThat(entity.getName()).isEqualTo("Only name");
        assertThat(out).isNotNull();
    }

    @Test
    void deleteById_ok() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        service.deleteById(1L);
        verify(repository).delete(entity);
    }
}
