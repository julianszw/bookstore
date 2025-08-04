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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(
            BookRepository bookRepository,
            AuthorRepository authorRepository,
            CategoryRepository categoryRepository,
            BookMapper bookMapper
    ) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookResponseDTO> getBooks() {
        log.info("Fetching all books");
        List<BookResponseDTO> books = bookRepository.findAll()
                .stream()
                .map(bookMapper::toResponseDto)
                .collect(Collectors.toList());
        log.info("Found {} books", books.size());
        return books;
    }

    @Override
    public BookResponseDTO findBookById(Long id) {
        log.info("Searching book by ID: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Book not found with ID: {}", id);
                    return new ResourceNotFoundException("Book not found with id: " + id);
                });
        log.info("Found book: {}", book.getTitle());
        return bookMapper.toResponseDto(book);
    }

    @Override
    public BookResponseDTO findBookByIsbn(String isbn) {
        log.info("Searching book by ISBN: {}", isbn);
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> {
                    log.warn("Book not found with ISBN: {}", isbn);
                    return new ResourceNotFoundException("Book not found with ISBN: " + isbn);
                });
        log.info("Found book: {}", book.getTitle());
        return bookMapper.toResponseDto(book);
    }

    @Override
    public BookResponseDTO createBook(BookRequestDTO dto) {
        log.info("Creating book: {} with ISBN {}", dto.getTitle(), dto.getIsbn());

        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> {
                    log.warn("Author not found with ID: {}", dto.getAuthorId());
                    return new ResourceNotFoundException("Author not found with id: " + dto.getAuthorId());
                });

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> {
                    log.warn("Category not found with ID: {}", dto.getCategoryId());
                    return new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId());
                });

        Book book = bookMapper.toEntity(dto);
        book.setAuthor(author);
        book.setCategory(category);

        Book saved = bookRepository.save(book);
        log.info("Book created with ID: {}", saved.getId());
        return bookMapper.toResponseDto(saved);
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO dto) {
        log.info("Updating book with ID: {}", id);

        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Book not found for update with ID: {}", id);
                    return new ResourceNotFoundException("Book not found with id: " + id);
                });

        existing.setTitle(dto.getTitle());
        existing.setIsbn(dto.getIsbn());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());

        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> {
                    log.warn("Author not found with ID: {}", dto.getAuthorId());
                    return new ResourceNotFoundException("Author not found with id: " + dto.getAuthorId());
                });
        existing.setAuthor(author);

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> {
                    log.warn("Category not found with ID: {}", dto.getCategoryId());
                    return new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId());
                });
        existing.setCategory(category);

        Book updated = bookRepository.save(existing);
        log.info("Book updated with ID: {}", updated.getId());
        return bookMapper.toResponseDto(updated);
    }

    @Override
    public void deleteBookById(Long id) {
        log.info("Attempting to delete book with ID: {}", id);
        if (!bookRepository.existsById(id)) {
            log.warn("Book not found for deletion with ID: {}", id);
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
        log.info("Book deleted with ID: {}", id);
    }

    @Override
    public List<BookResponseDTO> searchBookByKeyword(String keyword) {
        log.info("Searching books by keyword: '{}'", keyword);
        List<BookResponseDTO> results = bookRepository.findAll().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        (book.getDescription() != null && book.getDescription().toLowerCase().contains(keyword.toLowerCase())))
                .map(bookMapper::toResponseDto)
                .collect(Collectors.toList());
        log.info("Found {} books matching keyword '{}'", results.size(), keyword);
        return results;
    }

    @Override
    public List<BookResponseDTO> findBooksByCategory(String categoryName) {
        log.info("Searching books in category: '{}'", categoryName);
        List<Book> books = bookRepository.findByCategoryNameIgnoreCase(categoryName);
        if (books.isEmpty()) {
            log.warn("No books found in category: '{}'", categoryName);
            throw new ResourceNotFoundException("No books found in category: " + categoryName);
        }
        log.info("Found {} books in category '{}'", books.size(), categoryName);
        return books.stream()
                .map(bookMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
