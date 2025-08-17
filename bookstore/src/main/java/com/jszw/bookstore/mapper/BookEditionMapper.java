package com.jszw.bookstore.mapper;

import com.jszw.bookstore.domain.BookEdition;
import com.jszw.bookstore.dto.responseDto.BookEditionResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class BookEditionMapper {

    private final PublisherMapper publisherMapper;

    public BookEditionMapper(PublisherMapper publisherMapper) {
        this.publisherMapper = publisherMapper;
    }

    public BookEditionResponseDTO toDto(BookEdition entity) {
        if (entity == null) return null;
        return BookEditionResponseDTO.builder()
                .id(entity.getId())
                .edition(entity.getEditionLabel())
                .year(entity.getPublishedYear())
                .publisher(publisherMapper.toDto(entity.getPublisher()))
                .build();
    }
}
