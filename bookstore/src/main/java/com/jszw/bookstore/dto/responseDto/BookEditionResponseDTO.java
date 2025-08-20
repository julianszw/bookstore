package com.jszw.bookstore.dto.responseDto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookEditionResponseDTO {
    private Long id;
    private Integer year; // publishedYear
    private PublisherResponseDTO publisher;
    private String isbn13;
    private String isbn10;
}
