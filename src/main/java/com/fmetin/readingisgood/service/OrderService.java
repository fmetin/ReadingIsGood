package com.fmetin.readingisgood.service;

import com.fmetin.readingisgood.dto.OrderListByDateRequestDto;
import com.fmetin.readingisgood.dto.OrderRequestDto;
import com.fmetin.readingisgood.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {
    void order(OrderRequestDto request);

    OrderResponseDto getOrderById(Long orderId);

    List<OrderResponseDto> orderListByDate(OrderListByDateRequestDto request, Long customerId);
}
