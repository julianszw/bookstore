package com.jszw.bookstore.controllers;

import com.jszw.bookstore.dto.BookRequestDTO;
import com.jszw.bookstore.dto.BookResponseDTO;
import com.jszw.bookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findBookById(id));
    }


    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookResponseDTO> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.findBookByIsbn(isbn));
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO dto) {
        return ResponseEntity.ok(bookService.createBook(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @RequestBody BookRequestDTO dto) {
        return ResponseEntity.ok(bookService.updateBook(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponseDTO>> searchBooks(@RequestParam String keyword) {
        return ResponseEntity.ok(bookService.searchBookByKeyword(keyword));
    }
}
