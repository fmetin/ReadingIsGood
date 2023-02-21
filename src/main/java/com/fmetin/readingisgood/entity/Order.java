package com.fmetin.readingisgood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;
}
