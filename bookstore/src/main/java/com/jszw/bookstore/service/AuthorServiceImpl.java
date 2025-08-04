package com.jszw.bookstore.service;

import com.jszw.bookstore.domain.Author;
import com.jszw.bookstore.dto.responseDto.AuthorResponseDTO;
import com.jszw.bookstore.dto.requestDto.AuthorRequestDTO;
import com.jszw.bookstore.exception.ResourceNotFoundException;
import com.jszw.bookstore.mapper.AuthorMapper;
import com.jszw.bookstore.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public List<AuthorResponseDTO> getAllAuthors() {
        log.info("Searching all authors");
        List<AuthorResponseDTO> authors = authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
        log.info("Found {} authors", authors.size());
        return authors;
    }

    @Override
    public AuthorResponseDTO getAuthorById(Long id) {
        log.info("Searching author by ID: {}", id);
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Author not found with ID: {}", id);
                    return new ResourceNotFoundException("Author not found with id: " + id);
                });
        log.info("Found author: {} {}", author.getFirstName(), author.getLastName());
        return authorMapper.toDto(author);
    }

    @Override
    public AuthorResponseDTO createAuthor(AuthorRequestDTO dto) {
        log.info("Creating new author: {} {}", dto.getFirstName(), dto.getLastName());
        Author saved = authorRepository.save(authorMapper.toEntity(dto));
        log.info("Author created with ID: {}", saved.getId());
        return authorMapper.toDto(saved);
    }

    @Override
    public AuthorResponseDTO updateAuthor(Long id, AuthorRequestDTO dto) {
        log.info("Updating author with ID: {}", id);
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Author not found for update with ID: {}", id);
                    return new ResourceNotFoundException("Author not found with id: " + id);
                });

        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());

        Author updated = authorRepository.save(author);
        log.info("Author updated with ID: {}", updated.getId());
        return authorMapper.toDto(updated);
    }

    @Override
    public void deleteAuthor(Long id) {
        log.info("Attempting to delete author with ID: {}", id);
        if (!authorRepository.existsById(id)) {
            log.warn("Author not found for deletion with ID: {}", id);
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
        log.info("Author deleted with ID: {}", id);
    }

    @Override
    public List<AuthorResponseDTO> searchByName(String keyword) {
        log.info("Searching authors with keyword: {}", keyword);
        List<AuthorResponseDTO> results = authorRepository
                .findByFirstNameOrLastNameContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
        log.info("Found {} authors matching keyword '{}'", results.size(), keyword);
        return results;
    }
}
