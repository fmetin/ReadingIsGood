package com.fmetin.readingisgood.service;

import com.fmetin.readingisgood.dto.OrderDetailsDto;
import com.fmetin.readingisgood.dto.OrderRequestDto;
import com.fmetin.readingisgood.entity.Book;
import com.fmetin.readingisgood.entity.Order;
import com.fmetin.readingisgood.entity.OrderDetail;
import com.fmetin.readingisgood.enums.OrderStatusEnum;
import com.fmetin.readingisgood.mapper.OrderMapper;
import com.fmetin.readingisgood.service.impl.OrderTransactionServiceImpl;
import com.fmetin.readingisgood.shared.RestException;
import com.fmetin.readingisgood.shared.RestResponseCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderTransactionServiceTest {

    private OrderTransactionService orderTransactionService;
    @Mock
    private OrderService orderService;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private BookService bookService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        orderTransactionService = new OrderTransactionServiceImpl(orderService, orderMapper, bookService);
    }

    @Test
    void givenOrderRequestDto_whenOrderIsPlaced_thenOrderIsSavedAndCompleted() {
        OrderRequestDto orderRequestDto = createOrderRequestDto();

        Book book = new Book();
        book.setPrice(BigDecimal.valueOf(10));
        Mockito.when(bookService.findByBookIdToGetPrice(Mockito.anyLong())).thenReturn(book.getPrice());

        Order order = new Order();
        Mockito.when(orderMapper.mapOrderToOrderDetail((Order) Mockito.any())).thenReturn(new OrderDetail());
        Mockito.when(orderService.save((OrderRequestDto) Mockito.any())).thenReturn(order);
        Mockito.doNothing().when(orderService).order(Mockito.any());

        orderTransactionService.order(orderRequestDto);

        Assertions.assertEquals(OrderStatusEnum.COMPLETED.getStatus(), order.getStatus());
    }

    @Test
    void givenOrderRequestDto_whenOrderIsPlacedAndExceptionOccursDuringOrder_thenOrderIsSavedAsFailed() {
        OrderRequestDto orderRequestDto = createOrderRequestDto();

        Book book = new Book();
        book.setPrice(BigDecimal.valueOf(10));
        Mockito.when(bookService.findByBookIdToGetPrice(Mockito.anyLong())).thenReturn(book.getPrice());

        Order order = new Order();
        Mockito.when(orderMapper.mapOrderToOrderDetail((Order) Mockito.any())).thenReturn(new OrderDetail());
        Mockito.when(orderService.save((OrderRequestDto) Mockito.any())).thenReturn(order);
        Mockito.doThrow(new RestException(RestResponseCode.THERE_IS_NOT_STOCK)).when(orderService).order(Mockito.any());

        Assertions.assertThrows(RestException.class, () -> orderTransactionService.order(orderRequestDto));

        Assertions.assertEquals(OrderStatusEnum.FAILED.getStatus(), order.getStatus());
    }

    private OrderRequestDto createOrderRequestDto() {
        List<OrderDetailsDto> orderDetailsDtoList = new ArrayList<>();
        OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
        orderDetailsDto.setBookId(1L);
        orderDetailsDto.setCount(2);
        orderDetailsDtoList.add(orderDetailsDto);

        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setCustomerId(1L);
        orderRequestDto.setOrderList(orderDetailsDtoList);
        return orderRequestDto;
    }
}
