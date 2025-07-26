package com.jszw.bookstore.mapper;

import com.jszw.bookstore.domain.Book;
import com.jszw.bookstore.dto.BookRequestDTO;
import com.jszw.bookstore.dto.BookResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    private final AuthorMapper authorMapper;

    public BookMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    public BookRequestDTO toRequestDto(Book book) {
        return BookRequestDTO.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .description(book.getDescription())
                .price(book.getPrice())
                .authorId(book.getAuthor().getId())
                .category(book.getCategory())
                .build();
    }

    public BookResponseDTO toResponseDto(Book book) {
        return BookResponseDTO.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .description(book.getDescription())
                .price(book.getPrice())
                .category(book.getCategory())
                .author(authorMapper.toDto(book.getAuthor()))
                .build();
    }

    public Book toEntity(BookRequestDTO dto) {
        return Book.builder()
                .isbn(dto.getIsbn())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(dto.getCategory())
                // el Author se setea desde BookServiceImpl
                .build();
    }
}
