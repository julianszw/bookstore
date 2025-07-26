package com.jszw.bookstore.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String isbn;
    private String description;
    private Double price;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "author_id", nullable = false)
    private Author author;
    @ManyToOne @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookEdition> editions;

}
