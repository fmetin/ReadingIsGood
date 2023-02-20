package com.fmetin.readingisgood.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponseDto {
    private List<OrderDetailResponseDto> orderDetails;

    private int status;
}
