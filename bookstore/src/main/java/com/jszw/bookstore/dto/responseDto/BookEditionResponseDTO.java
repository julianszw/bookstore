package com.jszw.bookstore.dto.responseDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookEditionResponseDTO {
    private Long id;
    private String edition;
    private Integer year;
    private PublisherResponseDTO publisher;
}
