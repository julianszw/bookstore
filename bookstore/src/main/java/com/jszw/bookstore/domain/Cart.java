// Cart.java
package com.jszw.bookstore.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @ManyToMany
    private List<Book> books = new ArrayList<>();
}
