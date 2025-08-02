package com.jszw.bookstore.mapper;

import com.jszw.bookstore.domain.BookEdition;
import com.jszw.bookstore.dto.requestDto.BookEditionRequestDTO;
import com.jszw.bookstore.dto.responseDto.BookEditionResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class BookEditionMapper {

    private final PublisherMapper publisherMapper;

    public BookEditionMapper(PublisherMapper publisherMapper) {
        this.publisherMapper = publisherMapper;
    }

    public BookEdition toEntity(BookEditionRequestDTO dto) {
        return BookEdition.builder()
                .year(dto.getYear())
                .build(); // El book y el publisher se setean en el servicio
    }

    public BookEditionResponseDTO toDto(BookEdition entity) {
        return BookEditionResponseDTO.builder()
                .id(entity.getId())
                .year(entity.getYear())
                .publisher(publisherMapper.toDto(entity.getPublisher()))
                .build();
    }
}
