package com.jszw.bookstore.mapper;

import com.jszw.bookstore.domain.Book;
import com.jszw.bookstore.dto.BookDTO;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDTO toDto(Book book) {
        BookDTO dto = new BookDTO();
        dto.setIsbn(book.getIsbn());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setPrice(book.getPrice());

        if (book.getAuthor() != null) {
            dto.setAuthorId(book.getAuthor().getId());
        }

        return dto;
    }

    public Book toEntity(BookDTO dto) {
        Book book = new Book();
        book.setIsbn(dto.getIsbn());
        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setPrice(dto.getPrice());
        // El Author se setea desde el service, dado que el DTO solo contiene el ID
        return book;
    }
}
