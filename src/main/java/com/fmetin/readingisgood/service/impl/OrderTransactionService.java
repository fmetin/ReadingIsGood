package com.fmetin.readingisgood.service.impl;

import com.fmetin.readingisgood.dto.OrderRequestDto;
import com.fmetin.readingisgood.entity.Order;
import com.fmetin.readingisgood.enums.OrderStatusEnum;
import com.fmetin.readingisgood.service.OrderService;
import com.fmetin.readingisgood.shared.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderTransactionService {

    private final OrderService orderService;

    @Autowired
    public OrderTransactionService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Transactional
    public void order(OrderRequestDto orderRequestDto) {
        Order order = orderService.getOrder(orderRequestDto);
        try {
            orderService.order(orderRequestDto, order);
        } catch (RestException e) {
            order.setStatus(OrderStatusEnum.FAILED.getStatus());
            orderService.save(order);
            throw e;
        }
        order.setStatus(OrderStatusEnum.COMPLETED.getStatus());
        orderService.save(order);
    }
}
