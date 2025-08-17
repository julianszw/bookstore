package com.jszw.bookstore.service.impl;

import com.jszw.bookstore.domain.Publisher;
import com.jszw.bookstore.dto.requestDto.PublisherRequestDTO;
import com.jszw.bookstore.dto.responseDto.PublisherResponseDTO;
import com.jszw.bookstore.exception.ResourceNotFoundException;
import com.jszw.bookstore.mapper.PublisherMapper;
import com.jszw.bookstore.repository.PublisherRepository;
import com.jszw.bookstore.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository repository;
    private final PublisherMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<PublisherResponseDTO> getPublishers() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PublisherResponseDTO findById(Long id) {
        return mapper.toDto(getOrThrow(id));
    }

    @Override
    public PublisherResponseDTO create(PublisherRequestDTO dto) {
        Publisher publisher = Publisher.builder()
                .name(dto.getName())
                .country(dto.getCountry())
                .build();
        return mapper.toDto(repository.save(publisher));
    }

    @Override
    public PublisherResponseDTO update(Long id, PublisherRequestDTO dto) {
        Publisher existing = getOrThrow(id);
        existing.setName(dto.getName());
        existing.setCountry(dto.getCountry());
        return mapper.toDto(existing);
    }

    @Override
    public PublisherResponseDTO patch(Long id, PublisherRequestDTO dto) {
        Publisher existing = getOrThrow(id);
        Optional.ofNullable(dto.getName()).ifPresent(existing::setName);
        Optional.ofNullable(dto.getCountry()).ifPresent(existing::setCountry);
        return mapper.toDto(existing);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(getOrThrow(id));
    }

    private Publisher getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found: id=" + id));
    }
}
