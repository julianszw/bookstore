package com.jszw.bookstore.service;

import com.jszw.bookstore.dto.requestDto.PublisherRequestDTO;
import com.jszw.bookstore.dto.responseDto.PublisherResponseDTO;
import com.jszw.bookstore.mapper.PublisherMapper;
import com.jszw.bookstore.repository.PublisherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final Logger log = LoggerFactory.getLogger(PublisherServiceImpl.class);
    private final PublisherRepository repository;
    private final PublisherMapper mapper;

    public PublisherServiceImpl(PublisherRepository repository, PublisherMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PublisherResponseDTO createPublisher(PublisherRequestDTO dto) {
        log.info("Creating new publisher: {}", dto.getName());
        var saved = repository.save(mapper.toEntity(dto));
        log.info("Publisher created with ID: {}", saved.getId());
        return mapper.toDto(saved);
    }

    @Override
    public List<PublisherResponseDTO> getAll() {
        log.info("Fetching all publishers");
        List<PublisherResponseDTO> publishers = repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        log.info("Found {} publishers", publishers.size());
        return publishers;
    }
}
