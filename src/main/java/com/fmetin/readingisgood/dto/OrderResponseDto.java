package com.fmetin.readingisgood.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderResponseDto {
    private List<OrderDetailResponseDto> orderDetails;

    private int status;
    private BigDecimal totalAmount;
    private int countOfItems;
}
