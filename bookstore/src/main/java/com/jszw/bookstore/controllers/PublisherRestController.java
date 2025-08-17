package com.jszw.bookstore.controllers;

import com.jszw.bookstore.dto.requestDto.PublisherRequestDTO;
import com.jszw.bookstore.dto.responseDto.PublisherResponseDTO;
import com.jszw.bookstore.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/publishers")
public class PublisherRestController {

    private final PublisherService service;

    @GetMapping
    public ResponseEntity<List<PublisherResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getPublishers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PublisherResponseDTO> create(@Valid @RequestBody PublisherRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherResponseDTO> update(@PathVariable Long id,
                                                       @Valid @RequestBody PublisherRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PublisherResponseDTO> patch(@PathVariable Long id,
                                                      @RequestBody PublisherRequestDTO dto) {
        return ResponseEntity.ok(service.patch(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
