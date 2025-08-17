package com.jszw.bookstore.service;

import com.jszw.bookstore.dto.requestDto.PublisherRequestDTO;
import com.jszw.bookstore.dto.responseDto.PublisherResponseDTO;

import java.util.List;

public interface PublisherService {
    List<PublisherResponseDTO> getPublishers();
    PublisherResponseDTO findById(Long id);
    PublisherResponseDTO create(PublisherRequestDTO dto);
    PublisherResponseDTO update(Long id, PublisherRequestDTO dto);
    PublisherResponseDTO patch(Long id, PublisherRequestDTO dto);
    void deleteById(Long id);
}
