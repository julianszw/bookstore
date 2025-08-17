package com.jszw.bookstore.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class BookRequestDTO {

    @NotBlank
    private String title;

    private String isbn;
    private String description;

    @NotNull
    private Long authorId;

    // IDs de categorías
    private Set<Long> categoryIds;
}
