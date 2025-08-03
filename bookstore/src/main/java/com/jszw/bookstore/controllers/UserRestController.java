package com.jszw.bookstore.controllers;

import com.jszw.bookstore.dto.requestDto.UserRequestDTO;
import com.jszw.bookstore.dto.responseDto.UserResponseDTO;
import com.jszw.bookstore.mapper.UserMapper;
import com.jszw.bookstore.repository.UserRepository;
import com.jszw.bookstore.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserRestController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserRestController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(userMapper.toDto(savedUser));
    }
}
