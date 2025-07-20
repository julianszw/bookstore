package com.jszw.bookstore.service;

import com.jszw.bookstore.domain.Book;
import com.jszw.bookstore.dto.BookDTO;
import com.jszw.bookstore.exception.ResourceNotFoundException;
import com.jszw.bookstore.mapper.BookMapper;
import com.jszw.bookstore.repository.AuthorRepository;
import com.jszw.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookDTO> getBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO findBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public BookDTO findBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));
        return bookMapper.toDto(book);
    }

    @Override
    public BookDTO createBook(BookDTO dto) {
        Book book = bookMapper.toEntity(dto);
        Book saved = bookRepository.save(book);
        return bookMapper.toDto(saved);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO dto) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        // Podés actualizar solo los campos del DTO
        existing.setTitle(dto.getTitle());
        existing.setIsbn(dto.getIsbn());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());

        Book updated = bookRepository.save(existing);
        return bookMapper.toDto(updated);
    }

    @Override
    public void deleteBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    //Si querés hacer search con una query JPA custom, lo vemos luego.
    //Te usé un filtro por keyword muy básico (title o description contiene la palabra).
    @Override
    public List<BookDTO> searchBookByKeyword(String keyword) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        book.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
}
