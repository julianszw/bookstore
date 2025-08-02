package com.jszw.bookstore.controllers;

import com.jszw.bookstore.dto.requestDto.BookEditionRequestDTO;
import com.jszw.bookstore.dto.responseDto.BookEditionResponseDTO;
import com.jszw.bookstore.service.BookEditionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookstore/api/v1/editions")
public class BookEditionRestController {

    private final BookEditionService service;

    public BookEditionRestController(BookEditionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BookEditionResponseDTO> create(@RequestBody BookEditionRequestDTO dto) {
        return new ResponseEntity<>(service.createBookEdition(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<BookEditionResponseDTO> getAll() {
        return service.getAllBookEditions();
    }
}