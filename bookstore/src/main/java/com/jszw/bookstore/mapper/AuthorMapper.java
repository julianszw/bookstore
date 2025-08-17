package com.jszw.bookstore.mapper;

import com.jszw.bookstore.domain.Author;
import com.jszw.bookstore.dto.requestDto.AuthorRequestDTO;
import com.jszw.bookstore.dto.responseDto.AuthorResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public AuthorResponseDTO toDto(Author author) {
        if (author == null) return null;
        return AuthorResponseDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .bio(author.getBio())
                .build();
    }

    public Author toEntity(AuthorRequestDTO dto) {
        if (dto == null) return null;
        return Author.builder()
                .name(dto.getName())
                .bio(dto.getBio())
                .build();
    }
}
