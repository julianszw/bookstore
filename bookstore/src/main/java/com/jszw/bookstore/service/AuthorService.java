package com.jszw.bookstore.service;

import com.jszw.bookstore.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> getAllAuthors();
    AuthorDTO getAuthorById(Long id);
    AuthorDTO createAuthor(AuthorDTO dto);
    AuthorDTO updateAuthor(Long id, AuthorDTO dto);
    void deleteAuthor(Long id);
    List<AuthorDTO> searchByName(String keyword);
}
