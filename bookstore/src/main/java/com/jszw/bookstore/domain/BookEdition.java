package com.jszw.bookstore.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class BookEdition {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String editionLabel; // p.ej., "1ra Edición", "Reimpresión 2022"

    private Integer publishedYear;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    private Publisher publisher;

    private String imageUrl;
}
