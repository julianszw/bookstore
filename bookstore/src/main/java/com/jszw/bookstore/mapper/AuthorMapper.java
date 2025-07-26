package com.jszw.bookstore.mapper;

import com.jszw.bookstore.domain.Author;
import com.jszw.bookstore.dto.AuthorDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public AuthorDTO toDto(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }

    public Author toEntity(AuthorDTO dto) {
        return Author.builder()
                .id(dto.getId()) // opcional, solo si querés permitir update desde dto con id
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
    }

}


