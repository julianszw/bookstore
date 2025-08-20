package com.jszw.bookstore.service;

import com.jszw.bookstore.domain.Publisher;
import com.jszw.bookstore.dto.requestDto.PublisherRequestDTO;
import com.jszw.bookstore.dto.responseDto.PublisherResponseDTO;
import com.jszw.bookstore.exception.ResourceNotFoundException;
import com.jszw.bookstore.mapper.PublisherMapper;
import com.jszw.bookstore.repository.PublisherRepository;
import com.jszw.bookstore.service.impl.PublisherServiceImpl;
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
class PublisherServiceImplTest {

    @Mock PublisherRepository repository;
    @Mock PublisherMapper mapper;
    @InjectMocks PublisherServiceImpl service;

    Publisher entity;
    PublisherResponseDTO dtoRes;

    @BeforeEach
    void init() {
        entity = new Publisher();
        entity.setId(2L);
        entity.setName("Addison-Wesley");
        // entity.setCountry("US");  // si existe en tu entidad, no hace falta setearlo para este test

        dtoRes = PublisherResponseDTO.builder()
                .id(2L)
                .name("Addison-Wesley")
                // .country("US") // si tu DTO tiene country en el builder, podés incluirlo; NO usamos setters
                .build();
    }

    @Test
    void getPublishers_ok() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDto(entity)).thenReturn(dtoRes);

        var out = service.getPublishers();

        assertThat(out).hasSize(1);
        assertThat(out.get(0).getName()).isEqualTo("Addison-Wesley");
    }

    @Test
    void findById_found() {
        when(repository.findById(2L)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dtoRes);

        var out = service.findById(2L);
        assertThat(out.getName()).isEqualTo("Addison-Wesley");
    }

    @Test
    void findById_notFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_ok() {
        when(repository.save(any(Publisher.class))).thenAnswer(inv -> {
            Publisher p = inv.getArgument(0);
            p.setId(10L); return p;
        });
        when(mapper.toDto(any(Publisher.class))).thenReturn(dtoRes);

        var req = new PublisherRequestDTO();
        req.setName("Addison-Wesley");
        // req.setCountry("US"); // si tu request lo tiene, podés setearlo

        var out = service.create(req);

        verify(repository).save(any(Publisher.class));
        assertThat(out.getId()).isEqualTo(2L); // por el stub del mapper
    }

    @Test
    void update_ok_sets_fields_and_saves() {
        when(repository.findById(2L)).thenReturn(Optional.of(entity));
        when(repository.save(any(Publisher.class))).thenAnswer(inv -> inv.getArgument(0));
        when(mapper.toDto(any(Publisher.class))).thenReturn(dtoRes);

        var upd = new PublisherRequestDTO();
        upd.setName("Updated");
        // upd.setCountry("AR");

        ArgumentCaptor<Publisher> cap = ArgumentCaptor.forClass(Publisher.class);

        var out = service.update(2L, upd);

        verify(repository).save(cap.capture());
        Publisher saved = cap.getValue();
        assertThat(saved.getName()).isEqualTo("Updated");
        // si usás country en tu impl, podrías asertarlo también
        assertThat(out).isNotNull();
    }

    @Test
    void deleteById_ok() {
        when(repository.findById(2L)).thenReturn(Optional.of(entity));
        service.deleteById(2L);
        verify(repository).delete(entity);
    }
}
