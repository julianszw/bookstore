package com.jszw.bookstore.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();
    @ManyToMany
    private Set<Book> wishlist = new HashSet<>();

}
