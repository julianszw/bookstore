package com.jszw.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Desactiva protección CSRF (útil para APIs REST)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite cualquier solicitud sin autenticación
                );
        return http.build();
    }
}
