package com.jszw.bookstore.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {
    private String title;
    private String isbn;
    private String description;
    private Double price;
    private Long authorId;
}
