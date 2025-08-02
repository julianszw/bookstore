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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookEditionServiceImpl implements BookEditionService {

    private final BookEditionRepository bookEditionRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final BookEditionMapper mapper;

    public BookEditionServiceImpl(BookEditionRepository bookEditionRepository,
                                  BookRepository bookRepository,
                                  PublisherRepository publisherRepository,
                                  BookEditionMapper mapper) {
        this.bookEditionRepository = bookEditionRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.mapper = mapper;
    }

    @Override
    public BookEditionResponseDTO createBookEdition(BookEditionRequestDTO dto) {
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found"));

        BookEdition bookEdition = mapper.toEntity(dto);
        bookEdition.setBook(book);
        bookEdition.setPublisher(publisher);

        return mapper.toDto(bookEditionRepository.save(bookEdition));
    }

    @Override
    public List<BookEditionResponseDTO> getAllBookEditions() {
        return bookEditionRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
