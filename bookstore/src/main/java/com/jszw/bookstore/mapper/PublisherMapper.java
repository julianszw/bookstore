package com.jszw.bookstore.mapper;

import com.jszw.bookstore.domain.Publisher;
import com.jszw.bookstore.dto.requestDto.PublisherRequestDTO;
import com.jszw.bookstore.dto.responseDto.PublisherResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {

    public Publisher toEntity(PublisherRequestDTO dto) {
        return Publisher.builder()
                .name(dto.getName())
                .build();
    }

    public PublisherResponseDTO toDto(Publisher publisher) {
        return PublisherResponseDTO.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .build();
    }
}
