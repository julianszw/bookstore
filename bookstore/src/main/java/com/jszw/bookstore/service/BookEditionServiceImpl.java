package com.jszw.bookstore.service.impl;

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
import com.jszw.bookstore.service.BookEditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookEditionServiceImpl implements BookEditionService {

    private final BookEditionRepository repository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final BookEditionMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<BookEditionResponseDTO> getBookEditions() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookEditionResponseDTO findById(Long id) {
        return mapper.toDto(getOrThrow(id));
    }

    @Override
    public BookEditionResponseDTO create(BookEditionRequestDTO dto) {
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: id=" + dto.getBookId()));
        Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found: id=" + dto.getPublisherId()));

        BookEdition edition = BookEdition.builder()
                .book(book)
                .publisher(publisher)
                .editionNumber(dto.getEdition())
                .publishedYear(dto.getYear())
                .build();

        return mapper.toDto(repository.save(edition));
    }

    @Override
    public BookEditionResponseDTO update(Long id, BookEditionRequestDTO dto) {
        BookEdition existing = getOrThrow(id);

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: id=" + dto.getBookId()));
        Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found: id=" + dto.getPublisherId()));

        existing.setBook(book);
        existing.setPublisher(publisher);
        existing.setEditionNumber(dto.getEdition());
        existing.setPublishedYear(dto.getYear());

        // Opcional: explícito para evitar depender de dirty checking
        repository.save(existing);

        return mapper.toDto(existing);
    }

    @Override
    public BookEditionResponseDTO patch(Long id, BookEditionRequestDTO dto) {
        BookEdition existing = getOrThrow(id);

        Optional.ofNullable(dto.getBookId()).ifPresent(bookId -> {
            Book b = bookRepository.findById(bookId)
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found: id=" + bookId));
            existing.setBook(b);
        });

        Optional.ofNullable(dto.getPublisherId()).ifPresent(pubId -> {
            Publisher p = publisherRepository.findById(pubId)
                    .orElseThrow(() -> new ResourceNotFoundException("Publisher not found: id=" + pubId));
            existing.setPublisher(p);
        });

        Optional.ofNullable(dto.getEdition()).ifPresent(existing::setEditionNumber);
        Optional.ofNullable(dto.getYear()).ifPresent(existing::setPublishedYear);

        // Opcional: explícito para evitar depender de dirty checking
        repository.save(existing);

        return mapper.toDto(existing);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(getOrThrow(id));
    }

    private BookEdition getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BookEdition not found: id=" + id));
    }
}
