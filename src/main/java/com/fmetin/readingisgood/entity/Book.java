package com.fmetin.readingisgood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue
    private long bookId;
    private String name;

    private BigDecimal price;
    private int stock;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedDate;
}
