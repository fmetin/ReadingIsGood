package com.fmetin.readingisgood.dto;

import lombok.Data;

@Data
public class OrderListByDateRequestDto {
    private String startDate;
    private String endDate;
}
