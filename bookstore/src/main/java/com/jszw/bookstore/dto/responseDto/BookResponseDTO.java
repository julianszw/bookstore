package com.jszw.bookstore.dto.responseDto;

import com.jszw.bookstore.domain.Category;
import com.jszw.bookstore.dto.AuthorResponseDTO;
import com.jszw.bookstore.dto.requestDto.AuthorRequestDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDTO {
    private Long id;
    private String title;
    private String isbn;
    private Double price;
    private String description;
    private AuthorResponseDTO author;
    private Category category;
}
