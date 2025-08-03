// Review.java
package com.jszw.bookstore.domain;

import com.jszw.bookstore.converter.ReviewRatingConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bs_reviews")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Convert(converter = ReviewRatingConverter.class)
    @Column(nullable = false)
    private ReviewRating rating;


    @Column(columnDefinition = "TEXT")
    private String comment;
}

