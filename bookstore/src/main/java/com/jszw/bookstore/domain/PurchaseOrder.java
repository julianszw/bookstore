// Order.java
package com.jszw.bookstore.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "orders") // "order" es palabra reservada en SQL
public class PurchaseOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date purchaseOrderDate;
    private Double total;
    @ManyToOne
    private User user;
    @ManyToMany
    private List<Book> books = new ArrayList<>();
}
