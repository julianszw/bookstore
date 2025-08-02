package com.jszw.bookstore.controllers;

import com.jszw.bookstore.dto.requestDto.CategoryRequestDTO;
import com.jszw.bookstore.dto.responseDto.CategoryResponseDTO;
import com.jszw.bookstore.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookstore/api/v1/categories")
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody CategoryRequestDTO dto) {
        return new ResponseEntity<>(categoryService.createCategory(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<CategoryResponseDTO> getAll() {
        return categoryService.getAllCategories();
    }
}