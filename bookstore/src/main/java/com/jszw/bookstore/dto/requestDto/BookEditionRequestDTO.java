package com.jszw.bookstore.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookEditionRequestDTO {

    @NotNull
    private Long bookId;

    @NotNull
    private Long publisherId;

    @NotNull @Positive
    private Integer edition;   // número de edición

    @Positive
    private Integer year;
}
