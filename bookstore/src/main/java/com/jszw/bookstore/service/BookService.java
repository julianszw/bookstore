package com.jszw.bookstore.service;

import com.jszw.bookstore.dto.requestDto.BookRequestDTO;
import com.jszw.bookstore.dto.responseDto.BookResponseDTO;

import java.util.List;

public interface BookService {
    List<BookResponseDTO> getBooks();
    BookResponseDTO findBookById(Long id);
    BookResponseDTO findBookByIsbn(String isbn);
    BookResponseDTO createBook(BookRequestDTO dto);
    BookResponseDTO updateBook(Long id, BookRequestDTO dto);
    void deleteBookById(Long id);
    List<BookResponseDTO> searchBookByKeyword(String keyword);
    List<BookResponseDTO> findBooksByCategory(String categoryName);
}
