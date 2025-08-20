package com.jszw.bookstore.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookEditionAutoIsbnRequestDTO {
    @NotNull
    private Long bookId;

    @NotNull
    private Long publisherId;

    @Positive
    private Integer year;

    @NotBlank
    private String title;

    @NotBlank
    private String authorName;
}
