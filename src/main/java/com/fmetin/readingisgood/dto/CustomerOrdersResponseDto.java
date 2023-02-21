package com.fmetin.readingisgood.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerOrdersResponseDto {
    private List<OrderResponseDto> customerOrderList;

    private int totalPages;
    private long totalElements;
    private int pageNumber;
    private int size;

}
