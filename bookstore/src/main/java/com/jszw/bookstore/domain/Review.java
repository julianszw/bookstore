// Review.java
package com.jszw.bookstore.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer rating;
    @Column(length = 2000)
    private String comment;
    @ManyToOne
    private User user;
    @ManyToOne
    private Book book;
}
