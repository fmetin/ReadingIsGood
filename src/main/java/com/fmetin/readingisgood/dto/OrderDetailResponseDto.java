package com.fmetin.readingisgood.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDetailResponseDto {
    private long orderDetailId;
    private long orderId;
    private long bookId;
    private long customerId;
    private LocalDateTime createdDate;
}
