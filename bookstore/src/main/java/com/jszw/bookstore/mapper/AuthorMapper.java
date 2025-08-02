package com.jszw.bookstore.mapper;

import com.jszw.bookstore.domain.Author;
import com.jszw.bookstore.dto.AuthorResponseDTO;
import com.jszw.bookstore.dto.requestDto.AuthorRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public AuthorResponseDTO toDto(Author author) {
        return AuthorResponseDTO.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }

    public Author toEntity(AuthorRequestDTO dto) {
        return Author.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
    }
}
