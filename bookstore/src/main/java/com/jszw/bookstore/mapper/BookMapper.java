package com.jszw.bookstore.mapper;

import com.jszw.bookstore.domain.Book;

import com.jszw.bookstore.dto.requestDto.BookRequestDTO;
import com.jszw.bookstore.dto.responseDto.BookResponseDTO;
import com.jszw.bookstore.mapper.AuthorMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    private final AuthorMapper authorMapper;

    public BookMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    public BookResponseDTO toResponseDto(Book book) {
        return BookResponseDTO.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .description(book.getDescription())
                .price(book.getPrice())
                .author(authorMapper.toDto(book.getAuthor()))
                .category(book.getCategory())
                .build();
    }

    public Book toEntity(BookRequestDTO dto) {
        return Book.builder()
                .isbn(dto.getIsbn())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                // author, category y publisher se setean manualmente desde el Service
                .build();
    }
}
