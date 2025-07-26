package com.jszw.bookstore.service;

import com.jszw.bookstore.domain.Author;
import com.jszw.bookstore.domain.Book;
import com.jszw.bookstore.dto.BookRequestDTO;
import com.jszw.bookstore.dto.BookResponseDTO;
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
    public List<BookResponseDTO> getBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDTO findBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return bookMapper.toResponseDto(book);
    }

    @Override
    public BookResponseDTO findBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));
        return bookMapper.toResponseDto(book);
    }

    @Override
    public BookResponseDTO createBook(BookRequestDTO dto) {
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + dto.getAuthorId()));

        Book book = bookMapper.toEntity(dto);
        book.setAuthor(author);

        return bookMapper.toResponseDto(bookRepository.save(book));
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO dto) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        existing.setTitle(dto.getTitle());
        existing.setIsbn(dto.getIsbn());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());

        // Opcional: permitir cambiar autor
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + dto.getAuthorId()));
        existing.setAuthor(author);

        Book updated = bookRepository.save(existing);
        return bookMapper.toResponseDto(updated);
    }

    @Override
    public void deleteBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookResponseDTO> searchBookByKeyword(String keyword) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        book.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .map(bookMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponseDTO> findBooksByCategory(String categoryName) {
        List<Book> books = bookRepository.findByCategoryNameIgnoreCase(categoryName);
        if (books.isEmpty()) {
            throw new ResourceNotFoundException("No books found in category: " + categoryName);
        }

        return books.stream()
                .map(bookMapper::toResponseDto)
                .collect(Collectors.toList());
    }

}
