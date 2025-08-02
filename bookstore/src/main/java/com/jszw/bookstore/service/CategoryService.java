package com.jszw.bookstore.service;

import com.jszw.bookstore.dto.requestDto.CategoryRequestDTO;
import com.jszw.bookstore.dto.responseDto.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO dto);
    List<CategoryResponseDTO> getAllCategories();
}
