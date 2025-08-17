package com.jszw.bookstore.dto.requestDto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AuthorRequestDTO {
    private String name;
    private String bio;
}
