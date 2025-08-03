package com.jszw.bookstore.dto.requestDto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserRequestDTO {
    private String username;
    private String email;
    private String password;
}
