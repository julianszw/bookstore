package com.jszw.bookstore.service;

import com.jszw.bookstore.dto.requestDto.PublisherRequestDTO;
import com.jszw.bookstore.dto.responseDto.PublisherResponseDTO;
import com.jszw.bookstore.mapper.PublisherMapper;
import com.jszw.bookstore.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository repository;
    private final PublisherMapper mapper;

    public PublisherServiceImpl(PublisherRepository repository, PublisherMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PublisherResponseDTO createPublisher(PublisherRequestDTO dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public List<PublisherResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
