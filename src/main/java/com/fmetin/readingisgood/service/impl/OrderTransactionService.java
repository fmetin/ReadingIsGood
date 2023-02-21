package com.fmetin.readingisgood.service.impl;

import com.fmetin.readingisgood.dto.OrderRequestDto;
import com.fmetin.readingisgood.entity.Order;
import com.fmetin.readingisgood.entity.OrderDetail;
import com.fmetin.readingisgood.enums.OrderStatusEnum;
import com.fmetin.readingisgood.mapper.OrderMapper;
import com.fmetin.readingisgood.service.OrderService;
import com.fmetin.readingisgood.shared.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderTransactionService {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderTransactionService(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public void order(OrderRequestDto orderRequestDto) {
        Order order = orderService.save(orderRequestDto);
        orderRequestDto.getOrderList().forEach(orderDetailsDto -> {
            OrderDetail orderDetail = orderMapper.mapOrderToOrderDetail(order);
            orderDetail.setBookId(orderDetailsDto.getBookId());
            orderDetail.setCount(orderDetailsDto.getCount());
            orderService.saveOrderDetail(orderDetail);
        });
        try {
            orderService.order(orderRequestDto);
        } catch (RestException e) {
            order.setStatus(OrderStatusEnum.FAILED.getStatus());
            orderService.save(order);
            throw e;
        }
        order.setStatus(OrderStatusEnum.COMPLETED.getStatus());
        orderService.save(order);
    }
}
