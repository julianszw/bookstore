package com.jszw.bookstore.service;

import com.jszw.bookstore.dto.requestDto.AuthorRequestDTO;
import com.jszw.bookstore.dto.responseDto.AuthorResponseDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorResponseDTO> getAuthors();
    AuthorResponseDTO findById(Long id);
    AuthorResponseDTO create(AuthorRequestDTO dto);
    AuthorResponseDTO update(Long id, AuthorRequestDTO dto);
    AuthorResponseDTO patch(Long id, AuthorRequestDTO dto);
    void deleteById(Long id);
}
