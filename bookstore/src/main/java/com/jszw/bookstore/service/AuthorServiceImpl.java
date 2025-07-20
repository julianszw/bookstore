package com.jszw.bookstore.service;

import com.jszw.bookstore.domain.Author;
import com.jszw.bookstore.dto.AuthorDTO;
import com.jszw.bookstore.exception.ResourceNotFoundException;
import com.jszw.bookstore.mapper.AuthorMapper;
import com.jszw.bookstore.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        return authorMapper.toDto(author);
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO dto) {
        Author author = authorMapper.toEntity(dto);
        Author saved = authorRepository.save(author);
        return authorMapper.toDto(saved);
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO dto) {
        Author existing = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());

        Author updated = authorRepository.save(existing);
        return authorMapper.toDto(updated);
    }

    @Override
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
    }

    @Override
    public List<AuthorDTO> searchByName(String keyword) {
        return authorRepository
                .findByFirstNameOrLastNameContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }
}
