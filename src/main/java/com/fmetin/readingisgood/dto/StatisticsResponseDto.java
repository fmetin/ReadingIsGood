package com.fmetin.readingisgood.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StatisticsResponseDto {
    private String month;
    private int totalOrderCount;
    private int totalBookCount;
    private BigDecimal totalPurchasedAmount;

}
