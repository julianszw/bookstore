package com.jszw.bookstore.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bs_categories")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
