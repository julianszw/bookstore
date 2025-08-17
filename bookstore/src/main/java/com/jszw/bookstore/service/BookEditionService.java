package com.jszw.bookstore.service;

import com.jszw.bookstore.dto.requestDto.BookEditionRequestDTO;
import com.jszw.bookstore.dto.responseDto.BookEditionResponseDTO;

import java.util.List;

public interface BookEditionService {
    List<BookEditionResponseDTO> getBookEditions();
    BookEditionResponseDTO findById(Long id);
    BookEditionResponseDTO create(BookEditionRequestDTO dto);
    BookEditionResponseDTO update(Long id, BookEditionRequestDTO dto);
    BookEditionResponseDTO patch(Long id, BookEditionRequestDTO dto);
    void deleteById(Long id);
}
