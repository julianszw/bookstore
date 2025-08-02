package com.jszw.bookstore.service;

import com.jszw.bookstore.dto.requestDto.PublisherRequestDTO;
import com.jszw.bookstore.dto.responseDto.PublisherResponseDTO;

import java.util.List;

public interface PublisherService {
    PublisherResponseDTO createPublisher(PublisherRequestDTO dto);
    List<PublisherResponseDTO> getAll();
}
