package com.jszw.bookstore.mapper;

import com.jszw.bookstore.domain.Book;
import com.jszw.bookstore.dto.BookDTO;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDTO toDto (Book book) {
        BookDTO dto = new BookDTO();
        dto.setIsbn(book.getIsbn());
        dto.setDescription(book.getDescription());
        dto.setPrice(book.getPrice());
        return dto;
    }

    public Book toEntity(BookDTO dto) {
        Book book = new Book();
        book.setIsbn(dto.getIsbn());
        book.setDescription(dto.getDescription());
        book.setPrice(dto.getPrice());
        //book.setAuthor(dto.getAuthorId()); -> el autor se setea en el service
        return book;
    }
}

