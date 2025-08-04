package com.jszw.bookstore.service;

import com.jszw.bookstore.domain.Category;
import com.jszw.bookstore.dto.requestDto.CategoryRequestDTO;
import com.jszw.bookstore.dto.responseDto.CategoryResponseDTO;
import com.jszw.bookstore.mapper.CategoryMapper;
import com.jszw.bookstore.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        log.info("Creating new category: {}", dto.getName());
        Category saved = categoryRepository.save(categoryMapper.toEntity(dto));
        log.info("Category created with ID: {}", saved.getId());
        return categoryMapper.toDto(saved);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        log.info("Fetching all categories");
        List<CategoryResponseDTO> categories = categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
        log.info("Found {} categories", categories.size());
        return categories;
    }
}
