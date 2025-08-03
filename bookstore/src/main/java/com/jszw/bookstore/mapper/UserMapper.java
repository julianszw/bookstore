package com.jszw.bookstore.mapper;

import com.jszw.bookstore.domain.User;
import com.jszw.bookstore.dto.requestDto.UserRequestDTO;
import com.jszw.bookstore.dto.responseDto.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO dto) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword()) // se codifica en el service
                .build();
    }

    public UserResponseDTO toDto(User entity) {
        return UserResponseDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .build();
    }
}
