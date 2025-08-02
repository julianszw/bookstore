package com.jszw.bookstore.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublisherRequestDTO {
    @NotBlank
    private String name;
}
