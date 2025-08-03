package com.jszw.bookstore.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bs_publishers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String country;

    private String website;
}
