package com.fmetin.readingisgood.service.impl;

import com.fmetin.readingisgood.dto.*;
import com.fmetin.readingisgood.entity.Book;
import com.fmetin.readingisgood.entity.Order;
import com.fmetin.readingisgood.entity.OrderDetail;
import com.fmetin.readingisgood.enums.OrderStatusEnum;
import com.fmetin.readingisgood.mapper.OrderMapper;
import com.fmetin.readingisgood.repository.OrderDetailRepository;
import com.fmetin.readingisgood.repository.OrderRepository;
import com.fmetin.readingisgood.service.BookService;
import com.fmetin.readingisgood.service.OrderService;
import com.fmetin.readingisgood.shared.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fmetin.readingisgood.shared.RestResponseCode.*;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderMapper orderMapper;
    private final BookService bookService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, OrderMapper orderMapper, BookService bookService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.orderMapper = orderMapper;
        this.bookService = bookService;
    }

    @Override
    public void order(OrderRequestDto request, Order order) {

        try {
            List<OrderDetail> orderDetailList = new ArrayList<>();

            checkOrderList(request, orderDetailList, order);
            orderDetailRepository.saveAll(orderDetailList);

            order.setStatus(OrderStatusEnum.COMPLETED.getStatus());
            orderRepository.save(order);
        } catch (RestException restException) {
            log.error(restException.getResponseMessage());
            throw restException;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order getOrder(OrderRequestDto request) {
        Order order = new Order();
        order.setCreatedDate(LocalDateTime.now());
        order.setCustomerId(request.getCustomerId());
        order.setStatus(OrderStatusEnum.IN_PROGRESS.getStatus());
        order = orderRepository.save(order);
        return order;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty())
            throw new RestException(ORDER_NOT_FOUND);
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderDetails(orderMapper.mapOrderDetailListToOrderDetailResponseList(orderDetailList));
        orderResponseDto.setStatus(optionalOrder.get().getStatus());
        return orderResponseDto;
    }

    @Override
    public List<OrderResponseDto> orderListByDate(OrderListByDateRequestDto request, Long customerId) {
        LocalDateTime startDate = LocalDate.parse(request.getStartDate()).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(request.getEndDate()).atStartOfDay().plusDays(1).minusNanos(1);
        if (endDate.isBefore(startDate))
            throw new RestException(VALIDATION_ERROR, HttpStatus.BAD_REQUEST);
        List<OrderResponseDto> responseDtoList = new ArrayList<>();
        List<Order> orderList = orderRepository.findByCustomerIdAndCreatedDateBetween(
                customerId,
                startDate,
                endDate);
        for (Order order : orderList) {
            responseDtoList.add(getOrderById(order.getOrderId()));
        }
        return responseDtoList;
    }


    private void checkOrderList(OrderRequestDto request,
                               List<OrderDetail> orderDetailList,
                               Order order) {
        for (OrderDetailsDto orderDetailsDto : request.getOrderList()) {
            Book book = bookService.findByBookId(orderDetailsDto.getBookId());

            if (book.getStock() - orderDetailsDto.getCount() < 0)
                throw new RestException(THERE_IS_NOT_STOCK);

            OrderDetail orderDetail = orderMapper.mapOrderToOrderDetail(order);
            orderDetail.setBookId(book.getBookId());
            orderDetailList.add(orderDetail);

            UpdateBookStocksRequestDto updateBookStocksRequestDto = new UpdateBookStocksRequestDto();
            updateBookStocksRequestDto.setBookId(orderDetailsDto.getBookId());
            updateBookStocksRequestDto.setStock(book.getStock() - orderDetailsDto.getCount());
            bookService.updateBookStocks(updateBookStocksRequestDto);
        }
    }
}
