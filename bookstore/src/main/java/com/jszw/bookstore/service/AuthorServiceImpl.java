package com.jszw.bookstore.service.impl;

import com.jszw.bookstore.domain.Author;
import com.jszw.bookstore.dto.requestDto.AuthorRequestDTO;
import com.jszw.bookstore.dto.responseDto.AuthorResponseDTO;
import com.jszw.bookstore.exception.ResourceNotFoundException;
import com.jszw.bookstore.mapper.AuthorMapper;
import com.jszw.bookstore.repository.AuthorRepository;
import com.jszw.bookstore.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;
    private final AuthorMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorResponseDTO> getAuthors() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorResponseDTO findById(Long id) {
        return mapper.toDto(getOrThrow(id));
    }

    @Override
    public AuthorResponseDTO create(AuthorRequestDTO dto) {
        Author author = Author.builder()
                .name(dto.getName())
                .bio(dto.getBio())
                .build();
        return mapper.toDto(repository.save(author));
    }

    @Override
    public AuthorResponseDTO update(Long id, AuthorRequestDTO dto) {
        Author existing = getOrThrow(id);
        existing.setName(dto.getName());
        existing.setBio(dto.getBio());
        return mapper.toDto(existing);
    }

    @Override
    public AuthorResponseDTO patch(Long id, AuthorRequestDTO dto) {
        Author existing = getOrThrow(id);
        Optional.ofNullable(dto.getName()).ifPresent(existing::setName);
        Optional.ofNullable(dto.getBio()).ifPresent(existing::setBio);
        return mapper.toDto(existing);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(getOrThrow(id));
    }

    private Author getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found: id=" + id));
    }
}
