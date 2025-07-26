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
        return authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        return authorMapper.toDto(
                authorRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id))
        );
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO dto) {
        return authorMapper.toDto(
                authorRepository.save(authorMapper.toEntity(dto))
        );
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());

        return authorMapper.toDto(authorRepository.save(author));
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
