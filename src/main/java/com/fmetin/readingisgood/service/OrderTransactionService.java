package com.fmetin.readingisgood.service;

import com.fmetin.readingisgood.dto.OrderRequestDto;

public interface OrderTransactionService {
    void order(OrderRequestDto orderRequestDto);
}
