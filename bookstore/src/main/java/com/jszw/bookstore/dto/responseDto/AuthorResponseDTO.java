package com.jszw.bookstore.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorResponseDTO
{
    private Long id;
    private String firstName;
    private String lastName;
}
