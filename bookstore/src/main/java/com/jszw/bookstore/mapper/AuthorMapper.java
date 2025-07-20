package com.jszw.bookstore.mapper;

import com.jszw.bookstore.domain.Author;
import com.jszw.bookstore.dto.AuthorDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public AuthorDTO toDto(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setFirstName(author.getFirstName());
        dto.setLastName(author.getLastName());
        return dto;
    }

    public Author toEntity(AuthorDTO dto) {
        Author author = new Author();
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        return author;
    }
}
