package com.jszw.bookstore.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "book_editions",
        indexes = {
                @Index(name = "idx_book_editions_isbn13", columnList = "isbn13")
        })
public class BookEdition {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive
    @Column(name = "published_year")
    private Integer publishedYear;

    @Column(name = "isbn13", unique = true)
    private String isbn13;

    @Column(name = "isbn10")
    private String isbn10;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;
}
