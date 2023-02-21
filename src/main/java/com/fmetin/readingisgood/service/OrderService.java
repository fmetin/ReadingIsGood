package com.fmetin.readingisgood.service;

import com.fmetin.readingisgood.dto.OrderListByDateRequestDto;
import com.fmetin.readingisgood.dto.OrderRequestDto;
import com.fmetin.readingisgood.dto.OrderResponseDto;
import com.fmetin.readingisgood.entity.Order;
import com.fmetin.readingisgood.entity.OrderDetail;

import java.util.List;

public interface OrderService {
    void order(OrderRequestDto request);

    OrderResponseDto getOrderById(Long orderId);

    List<OrderResponseDto> orderListByDate(OrderListByDateRequestDto request, Long customerId);

    Order save(OrderRequestDto request);

    Order save(Order order);

    OrderDetail saveOrderDetail(OrderDetail orderDetail);

}
