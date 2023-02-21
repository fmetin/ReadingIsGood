package com.fmetin.readingisgood.service.impl;

import com.fmetin.readingisgood.dto.OrderRequestDto;
import com.fmetin.readingisgood.entity.Order;
import com.fmetin.readingisgood.entity.OrderDetail;
import com.fmetin.readingisgood.enums.OrderStatusEnum;
import com.fmetin.readingisgood.mapper.OrderMapper;
import com.fmetin.readingisgood.service.BookService;
import com.fmetin.readingisgood.service.OrderService;
import com.fmetin.readingisgood.service.OrderTransactionService;
import com.fmetin.readingisgood.shared.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class OrderTransactionServiceImpl implements OrderTransactionService {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    private final BookService bookService;

    @Autowired
    public OrderTransactionServiceImpl(OrderService orderService, OrderMapper orderMapper, BookService bookService) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.bookService = bookService;
    }

    @Override
    @Transactional
    public void order(OrderRequestDto orderRequestDto) {
        Order order = orderService.save(orderRequestDto);
        AtomicInteger countOfBooks = new AtomicInteger();
        var ref = new Object() {
            BigDecimal totalAmountOfOrder = BigDecimal.ZERO;
        };
        orderRequestDto.getOrderList().forEach(orderDetailsDto -> {
            OrderDetail orderDetail = orderMapper.mapOrderToOrderDetail(order);
            orderDetail.setBookId(orderDetailsDto.getBookId());
            orderDetail.setCount(orderDetailsDto.getCount());
            BigDecimal totalAmount = bookService.findByBookIdToGetPrice(orderDetailsDto.getBookId())
                    .multiply(BigDecimal.valueOf(orderDetailsDto.getCount()));
            orderDetail.setTotalAmount(totalAmount);
            orderService.saveOrderDetail(orderDetail);

            ref.totalAmountOfOrder = ref.totalAmountOfOrder.add(totalAmount);
            countOfBooks.addAndGet(orderDetailsDto.getCount());

        });
        order.setTotalAmount(ref.totalAmountOfOrder);
        order.setCountOfItems(countOfBooks.get());
        orderService.save(order);

        try {
            orderService.order(orderRequestDto);
        } catch (Exception e) {
            order.setStatus(OrderStatusEnum.FAILED.getStatus());
            orderService.save(order);
            throw e;
        }
        order.setStatus(OrderStatusEnum.COMPLETED.getStatus());
        orderService.save(order);
        log.info("end of order transaction service");
    }
}
