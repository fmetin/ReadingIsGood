package com.fmetin.readingisgood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ORDER_TRANSACTION")
public class Order {

    @Id
    @GeneratedValue
    private long orderId;
    private long customerId;
    private int status;
    private BigDecimal totalAmount;
    private int countOfItems;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;
}
