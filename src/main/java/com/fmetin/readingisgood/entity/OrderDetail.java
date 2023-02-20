package com.fmetin.readingisgood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ORDER_DETAIL")
public class OrderDetail {

    @Id
    @GeneratedValue
    private long orderDetailId;
    private long orderId;
    private long bookId;
    private long customerId;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

}
