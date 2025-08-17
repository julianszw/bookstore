package com.jszw.bookstore.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "book_editions")
public class BookEdition {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Número de edición (1, 2, 3, ...)
    @Positive
    @Column(name = "edition_number", nullable = false)
    private Integer editionNumber;

    private Integer publishedYear;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    private String imageUrl;
}
