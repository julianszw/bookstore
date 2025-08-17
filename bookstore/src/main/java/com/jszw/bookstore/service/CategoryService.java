package com.jszw.bookstore.service;

import com.jszw.bookstore.dto.requestDto.CategoryRequestDTO;
import com.jszw.bookstore.dto.responseDto.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDTO> getCategories();
    CategoryResponseDTO findById(Long id);
    CategoryResponseDTO create(CategoryRequestDTO dto);
    CategoryResponseDTO update(Long id, CategoryRequestDTO dto);
    CategoryResponseDTO patch(Long id, CategoryRequestDTO dto);
    void deleteById(Long id);
}
