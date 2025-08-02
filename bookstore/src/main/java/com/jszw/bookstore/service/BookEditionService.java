package com.jszw.bookstore.service;

import com.jszw.bookstore.dto.requestDto.BookEditionRequestDTO;
import com.jszw.bookstore.dto.responseDto.BookEditionResponseDTO;

import java.util.List;

public interface BookEditionService {
    BookEditionResponseDTO createBookEdition(BookEditionRequestDTO dto);
    List<BookEditionResponseDTO> getAllBookEditions();
}
