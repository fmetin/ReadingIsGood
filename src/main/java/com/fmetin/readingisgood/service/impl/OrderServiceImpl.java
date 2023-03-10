package com.fmetin.readingisgood.service.impl;

import com.fmetin.readingisgood.dto.*;
import com.fmetin.readingisgood.entity.Order;
import com.fmetin.readingisgood.entity.OrderDetail;
import com.fmetin.readingisgood.enums.OrderStatusEnum;
import com.fmetin.readingisgood.locker.DistributedLocker;
import com.fmetin.readingisgood.locker.LockExecutionResult;
import com.fmetin.readingisgood.mapper.OrderMapper;
import com.fmetin.readingisgood.repository.OrderDetailRepository;
import com.fmetin.readingisgood.repository.OrderRepository;
import com.fmetin.readingisgood.service.BookService;
import com.fmetin.readingisgood.service.OrderService;
import com.fmetin.readingisgood.shared.CommonProperties;
import com.fmetin.readingisgood.shared.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.fmetin.readingisgood.shared.RestResponseCode.*;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {



    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderMapper orderMapper;
    private final BookService bookService;

    private final DistributedLocker locker;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, OrderMapper orderMapper, BookService bookService, DistributedLocker locker) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.orderMapper = orderMapper;
        this.bookService = bookService;
        this.locker = locker;
    }

    @Override
    public void order(OrderRequestDto request) {

        try {
            for (OrderDetailsDto orderDetailsDto : request.getOrderList()) {
                String key = "bookdId:" + orderDetailsDto.getBookId();
                LockExecutionResult<String> result = locker.lock(key, CommonProperties.REDIS_LOCK_ACQUIRED_SECONDS, CommonProperties.REDIS_LOCK_TIMEOUT_SECONDS, () -> {
                    final long startTimestamp = System.currentTimeMillis();
                    final long lockTimeout = TimeUnit.SECONDS.toMillis(CommonProperties.REDIS_LOCK_TIMEOUT_SECONDS);
                    int stock = bookService.findByBookIdToGetStock(orderDetailsDto.getBookId());
                    log.info("book stock:{}", stock);
                    if (stock - orderDetailsDto.getCount() < 0)
                        throw new RestException(THERE_IS_NOT_STOCK);

                    UpdateBookStocksRequestDto updateBookStocksRequestDto = new UpdateBookStocksRequestDto();
                    updateBookStocksRequestDto.setBookId(orderDetailsDto.getBookId());
                    updateBookStocksRequestDto.setStock(stock - orderDetailsDto.getCount());
                    if (System.currentTimeMillis() - startTimestamp >= lockTimeout) {
                        throw new RestException(TRANSACTION_TIMEOUT);
                    }
                    bookService.updateBookStocks(updateBookStocksRequestDto);
                    return null;
                });
                if (result.hasException())
                    throw (RestException) result.exception;


            }
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
    public Order save(OrderRequestDto request) {
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public CustomerOrdersResponseDto getCustomerOrders(long customerId, Pageable pageable) {
        List<OrderResponseDto> customerOrderList = new ArrayList<>();
        CustomerOrdersResponseDto responseDto = new CustomerOrdersResponseDto();
        responseDto.setCustomerOrderList(customerOrderList);

        Page<Order> page = orderRepository.findByCustomerId(customerId, pageable);
        if (page.getContent().isEmpty())
            return responseDto;
        List<Order> orderList = page.getContent();
        orderList.forEach(order ->
                customerOrderList.add(getOrderById(order.getOrderId()))
        );
        responseDto.setSize(page.getSize());
        responseDto.setTotalElements(page.getTotalElements());
        responseDto.setTotalPages(page.getTotalPages());
        responseDto.setPageNumber(page.getNumber());
        return responseDto;
    }


    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty())
            throw new RestException(ORDER_NOT_FOUND);
        Order order = optionalOrder.get();
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderDetails(orderMapper.mapOrderDetailListToOrderDetailResponseList(orderDetailList));
        orderResponseDto.setStatus(order.getStatus());
        orderResponseDto.setCountOfItems(order.getCountOfItems());
        orderResponseDto.setTotalAmount(order.getTotalAmount());
        return orderResponseDto;
    }

    @Override
    public List<OrderResponseDto> orderListByDate(OrderListByDateRequestDto request, Long customerId) {
        LocalDateTime startDate = LocalDate.parse(request.getStartDate()).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(request.getEndDate()).atStartOfDay().plusDays(1).minusNanos(1);
        if (endDate.isBefore(startDate))
            throw new RestException(VALIDATION_ERROR, HttpStatus.BAD_REQUEST);
        List<OrderResponseDto> responseDtoList = new ArrayList<>();
        List<Order> orderList = orderRepository.findByCustomerIdAndCreatedDateBetweenOrderByCreatedDateDesc(
                customerId,
                startDate,
                endDate);
        for (Order order : orderList) {
            responseDtoList.add(getOrderById(order.getOrderId()));
        }
        return responseDtoList;
    }
}
