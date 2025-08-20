package com.jszw.bookstore.service;

import com.jszw.bookstore.domain.Author;
import com.jszw.bookstore.domain.Book;
import com.jszw.bookstore.domain.Category;
import com.jszw.bookstore.dto.requestDto.BookRequestDTO;
import com.jszw.bookstore.dto.responseDto.BookResponseDTO;
import com.jszw.bookstore.exception.ResourceNotFoundException;
import com.jszw.bookstore.mapper.BookMapper;
import com.jszw.bookstore.repository.AuthorRepository;
import com.jszw.bookstore.repository.BookRepository;
import com.jszw.bookstore.repository.CategoryRepository;
import com.jszw.bookstore.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock BookRepository bookRepository;
    @Mock AuthorRepository authorRepository;
    @Mock CategoryRepository categoryRepository;
    @Mock BookMapper bookMapper;
    @InjectMocks BookServiceImpl service;

    Author author;
    Book entity;
    BookResponseDTO dtoRes;

    @BeforeEach
    void init() {
        author = new Author();
        author.setId(7L);
        author.setName("Robert C. Martin");

        entity = new Book();
        entity.setId(3L);
        entity.setTitle("Clean Code");
        entity.setIsbn("111");
        entity.setAuthor(author);
        entity.setCategories(new HashSet<>());

        dtoRes = BookResponseDTO.builder()
                .id(3L)
                .title("Clean Code")
                .isbn("111")
                .build();
    }

    @Test
    void getBooks_ok() {
        when(bookRepository.findAll()).thenReturn(List.of(entity));
        when(bookMapper.toDto(entity)).thenReturn(dtoRes);

        var out = service.getBooks();

        assertThat(out).hasSize(1);
        assertThat(out.get(0).getIsbn()).isEqualTo("111");
    }

    @Test
    void findBookById_found() {
        when(bookRepository.findById(3L)).thenReturn(Optional.of(entity));
        when(bookMapper.toDto(entity)).thenReturn(dtoRes);

        var out = service.findBookById(3L);
        assertThat(out.getTitle()).isEqualTo("Clean Code");
    }

    @Test
    void findBookById_notFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findBookById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void findBookByIsbn_ok() {
        when(bookRepository.findByIsbn("111")).thenReturn(Optional.of(entity));
        when(bookMapper.toDto(entity)).thenReturn(dtoRes);

        var out = service.findBookByIsbn("111");
        assertThat(out.getId()).isEqualTo(3L);
    }

    @Test
    void findBookByIsbn_notFound() {
        when(bookRepository.findByIsbn("xxx")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findBookByIsbn("xxx"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createBook_ok_sets_author_and_fields() {
        when(authorRepository.findById(7L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> {
            Book b = inv.getArgument(0);
            b.setId(123L); return b;
        });
        when(bookMapper.toDto(any(Book.class))).thenReturn(dtoRes);

        var req = new BookRequestDTO();
        req.setTitle("Clean Code");
        req.setIsbn("111");
        req.setAuthorId(7L);

        var out = service.createBook(req);

        verify(bookRepository).save(any(Book.class));
        assertThat(out.getIsbn()).isEqualTo("111");
    }

    @Test
    void createBook_authorNotFound() {
        when(authorRepository.findById(7L)).thenReturn(Optional.empty());

        var req = new BookRequestDTO();
        req.setTitle("Clean Code");
        req.setIsbn("111");
        req.setAuthorId(7L);

        assertThatThrownBy(() -> service.createBook(req))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updateBook_ok_changes_fields_and_author() {
        when(bookRepository.findById(3L)).thenReturn(Optional.of(entity));
        when(authorRepository.findById(7L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));
        when(bookMapper.toDto(any(Book.class))).thenReturn(dtoRes);

        var upd = new BookRequestDTO();
        upd.setTitle("Updated");
        upd.setIsbn("222");
        upd.setAuthorId(7L);

        ArgumentCaptor<Book> cap = ArgumentCaptor.forClass(Book.class);

        var out = service.updateBook(3L, upd);

        verify(bookRepository).save(cap.capture());
        Book saved = cap.getValue();
        assertThat(saved.getTitle()).isEqualTo("Updated");
        assertThat(saved.getIsbn()).isEqualTo("222");
        assertThat(saved.getAuthor()).isEqualTo(author);
        assertThat(out).isNotNull();
    }

    @Test
    void updateBook_notFound() {
        when(bookRepository.findById(3L)).thenReturn(Optional.empty());
        var upd = new BookRequestDTO();
        assertThatThrownBy(() -> service.updateBook(3L, upd))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void searchBookByKeyword_uses_repo_search() {
        when(bookRepository.search("clean")).thenReturn(List.of(entity));
        when(bookMapper.toDto(entity)).thenReturn(dtoRes);

        var out = service.searchBookByKeyword("clean");

        assertThat(out).extracting(BookResponseDTO::getIsbn).contains("111");
    }

    @Test
    void findBooksByCategory_filters_by_name_case_insensitive() {
        Category c = new Category();
        c.setId(1L);
        c.setName("TDD");
        entity.getCategories().add(c);

        when(bookRepository.findAll()).thenReturn(List.of(entity));
        when(bookMapper.toDto(entity)).thenReturn(dtoRes);

        var out = service.findBooksByCategory("tdd");

        assertThat(out).hasSize(1);
    }

    @Test
    void deleteBookById_ok() {
        when(bookRepository.findById(3L)).thenReturn(Optional.of(entity));
        service.deleteBookById(3L);
        verify(bookRepository).delete(entity);
    }
}
