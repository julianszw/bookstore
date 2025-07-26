package com.jszw.bookstore.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookEdition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String publisher;
    private int year;
    private String language;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
