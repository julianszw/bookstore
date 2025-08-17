package com.jszw.bookstore.service.impl;

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
import com.jszw.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDTO> getBooks() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponseDTO findBookById(Long id) {
        return bookMapper.toDto(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponseDTO findBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with isbn=" + isbn));
        return bookMapper.toDto(book);
    }

    @Override
    public BookResponseDTO createBook(BookRequestDTO dto) {
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found: id=" + dto.getAuthorId()));

        Set<Category> categories = resolveCategories(dto.getCategoryIds());

        Book entity = Book.builder()
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .description(dto.getDescription())
                .author(author)
                .categories(categories)
                .build();

        return bookMapper.toDto(bookRepository.save(entity));
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO dto) {
        Book existing = getOrThrow(id);

        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found: id=" + dto.getAuthorId()));
        Set<Category> categories = resolveCategories(dto.getCategoryIds());

        existing.setTitle(dto.getTitle());
        existing.setIsbn(dto.getIsbn());
        existing.setDescription(dto.getDescription());
        existing.setAuthor(author);
        existing.setCategories(categories);

        return bookMapper.toDto(existing);
    }

    @Override
    public BookResponseDTO patchBook(Long id, BookRequestDTO dto) {
        Book existing = getOrThrow(id);

        Optional.ofNullable(dto.getTitle()).ifPresent(existing::setTitle);
        Optional.ofNullable(dto.getIsbn()).ifPresent(existing::setIsbn);
        Optional.ofNullable(dto.getDescription()).ifPresent(existing::setDescription);

        if (dto.getAuthorId() != null) {
            Author author = authorRepository.findById(dto.getAuthorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Author not found: id=" + dto.getAuthorId()));
            existing.setAuthor(author);
        }

        if (dto.getCategoryIds() != null) {
            existing.setCategories(resolveCategories(dto.getCategoryIds()));
        }

        return bookMapper.toDto(existing);
    }

    @Override
    public void deleteBookById(Long id) {
        Book existing = getOrThrow(id);
        bookRepository.delete(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDTO> searchBookByKeyword(String keyword) {
        // Simplificado: filtrar en memoria (idealmente usar Query/Spec)
        return bookRepository.findAll().stream()
                .filter(b -> {
                    String k = keyword == null ? "" : keyword.toLowerCase();
                    return (b.getTitle() != null && b.getTitle().toLowerCase().contains(k)) ||
                            (b.getIsbn() != null && b.getIsbn().toLowerCase().contains(k)) ||
                            (b.getDescription() != null && b.getDescription().toLowerCase().contains(k));
                })
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDTO> findBooksByCategory(String categoryName) {
        return bookRepository.findAll().stream()
                .filter(b -> b.getCategories().stream().anyMatch(c -> c.getName().equalsIgnoreCase(categoryName)))
                .map(bookMapper::toDto)
                .toList();
    }

    private Book getOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: id=" + id));
    }

    private Set<Category> resolveCategories(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) return new HashSet<>();
        List<Category> list = categoryRepository.findAllById(ids);
        if (list.size() != ids.size()) {
            throw new ResourceNotFoundException("One or more categories not found: " + ids);
        }
        return new HashSet<>(list);
    }
}
