package com.jszw.bookstore.service;


import com.jszw.bookstore.dto.responseDto.AuthorResponseDTO;
import com.jszw.bookstore.dto.requestDto.AuthorRequestDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorResponseDTO> getAllAuthors();
    AuthorResponseDTO getAuthorById(Long id);
    AuthorResponseDTO createAuthor(AuthorRequestDTO dto);
    AuthorResponseDTO updateAuthor(Long id, AuthorRequestDTO dto);
    void deleteAuthor(Long id);
    List<AuthorResponseDTO> searchByName(String keyword);
}
