package com.jszw.bookstore.service;

import com.jszw.bookstore.dto.BookDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> getBooks();
    BookDTO findBookById(Long id);
    BookDTO findBookByIsbn(String isbn);
    BookDTO createBook(BookDTO dto);
    BookDTO updateBook(Long id, BookDTO dto);
    void deleteBookById(Long id);
    List<BookDTO> searchBookByKeyword(String keyword);
}
