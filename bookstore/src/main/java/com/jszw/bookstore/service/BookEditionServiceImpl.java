package com.jszw.bookstore.service.impl;

import com.jszw.bookstore.domain.Book;
import com.jszw.bookstore.domain.BookEdition;
import com.jszw.bookstore.domain.Publisher;
import com.jszw.bookstore.dto.requestDto.BookEditionAutoIsbnRequestDTO;
import com.jszw.bookstore.dto.requestDto.BookEditionRequestDTO;
import com.jszw.bookstore.dto.responseDto.BookEditionResponseDTO;
import com.jszw.bookstore.exception.ResourceNotFoundException;
import com.jszw.bookstore.external.googlebooks.GoogleBooksClient;
import com.jszw.bookstore.mapper.BookEditionMapper;
import com.jszw.bookstore.repository.BookEditionRepository;
import com.jszw.bookstore.repository.BookRepository;
import com.jszw.bookstore.repository.PublisherRepository;
import com.jszw.bookstore.service.BookEditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookEditionServiceImpl implements BookEditionService {

    private final BookEditionRepository repository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final BookEditionMapper mapper;

    // Cliente para Google Books
    private final GoogleBooksClient googleBooksClient;

    @Override
    @Transactional(readOnly = true)
    public List<BookEditionResponseDTO> getBookEditions() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookEditionResponseDTO findById(Long id) {
        return mapper.toDto(getOrThrow(id));
    }

    private static String normalizeIsbn(String raw) {
        if (raw == null) return null;
        String s = raw.replaceAll("[^0-9Xx]", "").toUpperCase();
        return s.isBlank() ? null : s;
    }

    @Override
    @Transactional
    public BookEditionResponseDTO create(BookEditionRequestDTO dto) {
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: id=" + dto.getBookId()));
        Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found: id=" + dto.getPublisherId()));

        String isbn13 = normalizeIsbn(dto.getIsbn13());
        String isbn10 = normalizeIsbn(dto.getIsbn10());

        if (isbn13 != null && repository.existsByIsbn13(isbn13)) {
            throw new DataIntegrityViolationException("Ya existe una edición con ISBN-13 " + isbn13);
        }

        BookEdition entity = BookEdition.builder()
                .book(book)
                .publisher(publisher)
                .publishedYear(dto.getYear())
                .isbn13(isbn13)
                .isbn10(isbn10)
                .build();

        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public BookEditionResponseDTO update(Long id, BookEditionRequestDTO dto) {
        BookEdition existing = getOrThrow(id);

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: id=" + dto.getBookId()));
        Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found: id=" + dto.getPublisherId()));

        existing.setPublishedYear(dto.getYear());
        existing.setPublisher(publisher);
        existing.setBook(book);

        String newIsbn13 = normalizeIsbn(dto.getIsbn13());
        String newIsbn10 = normalizeIsbn(dto.getIsbn10());

        if (newIsbn13 != null && !Objects.equals(newIsbn13, existing.getIsbn13())) {
            if (repository.existsByIsbn13(newIsbn13)) {
                throw new DataIntegrityViolationException("Ya existe una edición con ISBN-13 " + newIsbn13);
            }
            existing.setIsbn13(newIsbn13);
        } else if (newIsbn13 == null) {
            existing.setIsbn13(null);
        }

        existing.setIsbn10(newIsbn10);

        repository.save(existing);
        return mapper.toDto(existing);
    }

    @Override
    @Transactional
    public BookEditionResponseDTO patch(Long id, BookEditionRequestDTO dto) {
        BookEdition existing = getOrThrow(id);

        Optional.ofNullable(dto.getYear()).ifPresent(existing::setPublishedYear);

        if (dto.getPublisherId() != null) {
            Publisher p = publisherRepository.findById(dto.getPublisherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Publisher not found: id=" + dto.getPublisherId()));
            existing.setPublisher(p);
        }

        if (dto.getBookId() != null) {
            Book b = bookRepository.findById(dto.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found: id=" + dto.getBookId()));
            existing.setBook(b);
        }

        if (dto.getIsbn13() != null) {
            String n = normalizeIsbn(dto.getIsbn13());
            if (n != null && !Objects.equals(n, existing.getIsbn13())) {
                if (repository.existsByIsbn13(n)) {
                    throw new DataIntegrityViolationException("Ya existe una edición con ISBN-13 " + n);
                }
                existing.setIsbn13(n);
            } else if (n == null) {
                existing.setIsbn13(null);
            }
        }

        if (dto.getIsbn10() != null) {
            existing.setIsbn10(normalizeIsbn(dto.getIsbn10()));
        }

        repository.save(existing);
        return mapper.toDto(existing);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.delete(getOrThrow(id));
    }

    private BookEdition getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BookEdition not found: id=" + id));
    }

    // ========= Auto-ISBN con Google Books =========
    @Override
    @Transactional
    public BookEditionResponseDTO createWithAutoIsbn(BookEditionAutoIsbnRequestDTO dto) {
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: id=" + dto.getBookId()));
        Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found: id=" + dto.getPublisherId()));

        var maybe = googleBooksClient.pickBestIsbn(dto.getTitle(), dto.getAuthorName());
        if (maybe.isEmpty()) {
            throw new IllegalArgumentException("No se encontró ISBN confiable para: " + dto.getTitle()
                    + " / " + dto.getAuthorName());
        }

        var pair = maybe.get();
        String isbn13 = normalizeIsbn(pair.isbn13());
        String isbn10 = normalizeIsbn(pair.isbn10());

        if (isbn13 != null && repository.existsByIsbn13(isbn13)) {
            throw new DataIntegrityViolationException("Ya existe una edición con ISBN-13 " + isbn13);
        }

        BookEdition entity = BookEdition.builder()
                .book(book)
                .publisher(publisher)
                .publishedYear(dto.getYear())
                .isbn13(isbn13)
                .isbn10(isbn10)
                .build();

        entity = repository.save(entity);
        return mapper.toDto(entity);
    }
}
