package com.jszw.bookstore.dto.requestDto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PublisherRequestDTO {
    private String name;
    private String country;
}
