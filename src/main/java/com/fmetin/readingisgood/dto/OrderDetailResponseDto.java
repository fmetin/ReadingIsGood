package com.fmetin.readingisgood.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDetailResponseDto {
    private long orderDetailId;
    private long orderId;
    private long bookId;
    private long customerId;
    private long count;
    private BigDecimal totalAmount;
    private LocalDateTime createdDate;
}
