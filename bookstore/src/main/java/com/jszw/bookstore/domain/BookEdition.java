package com.jszw.bookstore.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bs_book_editions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookEdition {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "publisher_id")
    private Publisher publisher;
    private int year;
    private String language;
    @ManyToOne @JoinColumn(name = "book_id")
    private Book book;
}
