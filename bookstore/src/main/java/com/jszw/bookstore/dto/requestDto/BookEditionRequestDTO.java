package com.jszw.bookstore.dto.requestDto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookEditionRequestDTO {
    @NotNull
    private Long bookId;

    @NotNull
    private Long publisherId;

    @NotBlank
    private String edition;

    @Positive
    private Integer year;
}
