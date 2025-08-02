package com.jszw.bookstore.dto.requestDto;

import com.jszw.bookstore.domain.Category;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestDTO {

    @NotBlank
    private String title;
    @NotBlank
    private String isbn;
    @NotNull
    @Positive
    private Double price;
    private String description;
    @NotNull
    private Long authorId;
    @NotNull
    private Category category;
}
