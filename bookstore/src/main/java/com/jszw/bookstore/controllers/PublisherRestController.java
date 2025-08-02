package com.jszw.bookstore.controllers;

import com.jszw.bookstore.dto.requestDto.PublisherRequestDTO;
import com.jszw.bookstore.dto.responseDto.PublisherResponseDTO;
import com.jszw.bookstore.service.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookstore/api/v1/publishers")
public class PublisherRestController {

    private final PublisherService service;

    public PublisherRestController(PublisherService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PublisherResponseDTO> create(@RequestBody PublisherRequestDTO dto) {
        return new ResponseEntity<>(service.createPublisher(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<PublisherResponseDTO> getAll() {
        return service.getAll();
    }
}
