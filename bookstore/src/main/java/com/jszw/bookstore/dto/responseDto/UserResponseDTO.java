package com.jszw.bookstore.dto.responseDto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
}
