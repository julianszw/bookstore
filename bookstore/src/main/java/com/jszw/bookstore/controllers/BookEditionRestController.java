package com.jszw.bookstore.controllers;

import com.jszw.bookstore.dto.requestDto.BookEditionRequestDTO;
import com.jszw.bookstore.dto.responseDto.BookEditionResponseDTO;
import com.jszw.bookstore.service.BookEditionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book-editions")
public class BookEditionRestController {

    private final BookEditionService service;

    @GetMapping
    public ResponseEntity<List<BookEditionResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getBookEditions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookEditionResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<BookEditionResponseDTO> create(@Valid @RequestBody BookEditionRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookEditionResponseDTO> update(@PathVariable Long id,
                                                         @Valid @RequestBody BookEditionRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookEditionResponseDTO> patch(@PathVariable Long id,
                                                        @RequestBody BookEditionRequestDTO dto) {
        return ResponseEntity.ok(service.patch(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
