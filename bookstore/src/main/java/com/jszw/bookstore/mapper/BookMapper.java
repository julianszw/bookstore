package com.jszw.bookstore.mapper;

import com.jszw.bookstore.domain.Book;
import com.jszw.bookstore.domain.Category;
import com.jszw.bookstore.domain.Author;
import com.jszw.bookstore.dto.responseDto.AuthorResponseDTO;
import com.jszw.bookstore.dto.responseDto.BookResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public BookResponseDTO toDto(Book book) {
        if (book == null) return null;
        return BookResponseDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .description(book.getDescription())
                .author(toAuthorDto(book.getAuthor()))
                .categories(toCategoryNames(book.getCategories()))
                .build();
    }

    private AuthorResponseDTO toAuthorDto(Author author) {
        if (author == null) return null;
        return AuthorResponseDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .bio(author.getBio())
                .build();
    }

    private Set<String> toCategoryNames(Set<Category> categories) {
        if (categories == null || categories.isEmpty()) return Set.of();
        return categories.stream()
                .map(Category::getName)
                .collect(Collectors.toSet());
    }
}
