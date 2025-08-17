package com.jszw.bookstore.service.impl;

import com.jszw.bookstore.domain.Category;
import com.jszw.bookstore.dto.requestDto.CategoryRequestDTO;
import com.jszw.bookstore.dto.responseDto.CategoryResponseDTO;
import com.jszw.bookstore.exception.ResourceNotFoundException;
import com.jszw.bookstore.mapper.CategoryMapper;
import com.jszw.bookstore.repository.CategoryRepository;
import com.jszw.bookstore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getCategories() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDTO findById(Long id) {
        return mapper.toDto(getOrThrow(id));
    }

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        Category entity = Category.builder().name(dto.getName()).build();
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
        Category existing = getOrThrow(id);
        existing.setName(dto.getName());
        return mapper.toDto(existing);
    }

    @Override
    public CategoryResponseDTO patch(Long id, CategoryRequestDTO dto) {
        Category existing = getOrThrow(id);
        Optional.ofNullable(dto.getName()).ifPresent(existing::setName);
        return mapper.toDto(existing);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(getOrThrow(id));
    }

    private Category getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: id=" + id));
    }
}
