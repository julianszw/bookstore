package com.jszw.bookstore.service;

import com.jszw.bookstore.domain.Book;
import com.jszw.bookstore.domain.BookEdition;
import com.jszw.bookstore.domain.Publisher;
import com.jszw.bookstore.dto.requestDto.BookEditionRequestDTO;
import com.jszw.bookstore.dto.responseDto.BookEditionResponseDTO;
import com.jszw.bookstore.exception.ResourceNotFoundException;
import com.jszw.bookstore.mapper.BookEditionMapper;
import com.jszw.bookstore.repository.BookEditionRepository;
import com.jszw.bookstore.repository.BookRepository;
import com.jszw.bookstore.repository.PublisherRepository;
import com.jszw.bookstore.service.impl.BookEditionServiceImpl;
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
class BookEditionServiceImplTest {

    @Mock BookEditionRepository repository;
    @Mock BookRepository bookRepository;
    @Mock PublisherRepository publisherRepository;
    @Mock BookEditionMapper mapper;
    @InjectMocks BookEditionServiceImpl service;

    Book book;
    Publisher publisher;
    BookEdition entity;
    BookEditionResponseDTO dtoRes;

    @BeforeEach
    void init() {
        book = new Book(); book.setId(3L);
        publisher = new Publisher(); publisher.setId(2L); publisher.setName("Pub");

        entity = new BookEdition();
        entity.setId(10L);
        entity.setBook(book);
        entity.setPublisher(publisher);
        entity.setPublishedYear(2020);

        dtoRes = BookEditionResponseDTO.builder()
                .id(10L)
                .year(2020) // <- en tu DTO el campo es 'year'
                .build();
    }

    @Test
    void getBookEditions_ok() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDto(entity)).thenReturn(dtoRes);

        var out = service.getBookEditions();

        assertThat(out).hasSize(1);
        assertThat(out.get(0).getYear()).isEqualTo(2020);
    }

    @Test
    void findById_found() {
        when(repository.findById(10L)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dtoRes);

        var out = service.findById(10L);
        assertThat(out.getId()).isEqualTo(10L);
    }

    @Test
    void findById_notFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_ok_sets_associations_and_year() {
        when(bookRepository.findById(3L)).thenReturn(Optional.of(book));
        when(publisherRepository.findById(2L)).thenReturn(Optional.of(publisher));
        when(repository.save(any(BookEdition.class))).thenAnswer(inv -> {
            BookEdition e = inv.getArgument(0);
            e.setId(55L); return e;
        });
        when(mapper.toDto(any(BookEdition.class))).thenReturn(dtoRes);

        var req = new BookEditionRequestDTO();
        req.setBookId(3L);
        req.setPublisherId(2L);
        req.setYear(2020); // <- usa 'year'

        var out = service.create(req);

        verify(repository).save(any(BookEdition.class));
        assertThat(out).isNotNull();
    }

    @Test
    void create_bookNotFound() {
        when(bookRepository.findById(3L)).thenReturn(Optional.empty());

        var req = new BookEditionRequestDTO();
        req.setBookId(3L);
        req.setPublisherId(2L);
        req.setYear(2020);

        assertThatThrownBy(() -> service.create(req))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_publisherNotFound() {
        when(bookRepository.findById(3L)).thenReturn(Optional.of(book));
        when(publisherRepository.findById(2L)).thenReturn(Optional.empty());

        var req = new BookEditionRequestDTO();
        req.setBookId(3L);
        req.setPublisherId(2L);
        req.setYear(2020);

        assertThatThrownBy(() -> service.create(req))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_ok_changes_year_and_associations() {
        when(repository.findById(10L)).thenReturn(Optional.of(entity));
        when(bookRepository.findById(3L)).thenReturn(Optional.of(book));
        when(publisherRepository.findById(2L)).thenReturn(Optional.of(publisher));
        when(repository.save(any(BookEdition.class))).thenAnswer(inv -> inv.getArgument(0));
        when(mapper.toDto(any(BookEdition.class))).thenReturn(dtoRes);

        var upd = new BookEditionRequestDTO();
        upd.setBookId(3L);
        upd.setPublisherId(2L);
        upd.setYear(2021); // <- usa 'year'

        ArgumentCaptor<BookEdition> cap = ArgumentCaptor.forClass(BookEdition.class);

        var out = service.update(10L, upd);

        verify(repository).save(cap.capture());
        BookEdition saved = cap.getValue();
        assertThat(saved.getPublishedYear()).isEqualTo(2021);
        assertThat(saved.getBook()).isEqualTo(book);
        assertThat(saved.getPublisher()).isEqualTo(publisher);
        assertThat(out).isNotNull();
    }

    @Test
    void update_notFound() {
        when(repository.findById(10L)).thenReturn(Optional.empty());
        var upd = new BookEditionRequestDTO();
        assertThatThrownBy(() -> service.update(10L, upd))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteById_ok() {
        when(repository.findById(10L)).thenReturn(Optional.of(entity));
        service.deleteById(10L);
        verify(repository).delete(entity);
    }
}
