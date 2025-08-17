package com.jszw.bookstore.dto.responseDto;

import lombok.*;
import java.util.Set;

/**
 * Read-only view of a Book with a light embedded Author and category names.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class BookResponseDTO {

    private Long id;
    private String title;
    private String isbn;
    private String description;

    // Embedded author (id, name, bio)
    private AuthorResponseDTO author;

    // Category names for easy display
    private Set<String> categories;
}
