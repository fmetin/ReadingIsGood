package com.fmetin.readingisgood.service;

import com.fmetin.readingisgood.dto.OrderDetailResponseDto;
import com.fmetin.readingisgood.dto.OrderRequestDto;
import com.fmetin.readingisgood.dto.OrderResponseDto;
import com.fmetin.readingisgood.entity.Order;
import com.fmetin.readingisgood.entity.OrderDetail;
import com.fmetin.readingisgood.enums.OrderStatusEnum;
import com.fmetin.readingisgood.locker.DistributedLocker;
import com.fmetin.readingisgood.mapper.OrderMapper;
import com.fmetin.readingisgood.repository.OrderDetailRepository;
import com.fmetin.readingisgood.repository.OrderRepository;
import com.fmetin.readingisgood.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderServiceImpl orderService;
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private OrderMapper orderMapper;
    private BookService bookService;
    private DistributedLocker locker;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderDetailRepository = mock(OrderDetailRepository.class);
        orderMapper = mock(OrderMapper.class);
        bookService = mock(BookService.class);
        locker = mock(DistributedLocker.class);

        orderService = new OrderServiceImpl(orderRepository, orderDetailRepository, orderMapper, bookService, locker);
    }

    @Test
    void save_whenValidInput_thenShouldReturnSavedOrder() {
        // Given
        OrderRequestDto orderRequest = new OrderRequestDto();
        orderRequest.setCustomerId(1L);

        Order savedOrder = new Order();
        savedOrder.setOrderId(1L);
        savedOrder.setCustomerId(1L);
        savedOrder.setStatus(OrderStatusEnum.IN_PROGRESS.getStatus());
        savedOrder.setCreatedDate(LocalDateTime.now());

        when(orderRepository.save(ArgumentMatchers.any(Order.class))).thenReturn(savedOrder);

        // When
        Order result = orderService.save(orderRequest);

        // Then
        assertNotNull(result);
        assertEquals(savedOrder.getCustomerId(), result.getCustomerId());
        assertEquals(savedOrder.getStatus(), result.getStatus());
        assertEquals(savedOrder.getCreatedDate(), result.getCreatedDate());
    }

    @Test
    public void testSaveOrder() {
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);

        Order savedOrder = orderService.save(order);
        assertEquals(order, savedOrder);
    }

    @Test
    public void testSaveOrderDetail() {
        OrderDetail orderDetail = new OrderDetail();
        when(orderDetailRepository.save(orderDetail)).thenReturn(orderDetail);

        OrderDetail savedOrderDetail = orderService.saveOrderDetail(orderDetail);
        assertEquals(orderDetail, savedOrderDetail);
    }

    @Test
    void givenOrderId_whenGetOrderById_thenReturnOrder() {
        Long orderId = 1L;

        Order order = new Order();
        order.setOrderId(orderId);
        order.setStatus(OrderStatusEnum.COMPLETED.getStatus());

        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailId(1L);
        orderDetail.setOrderId(orderId);
        orderDetail.setBookId(1L);
        orderDetail.setCount(2);
        orderDetails.add(orderDetail);

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setStatus(OrderStatusEnum.COMPLETED.getStatus());
        OrderDetailResponseDto orderDetailResponseDto = new OrderDetailResponseDto();
        orderDetailResponseDto.setOrderDetailId(1L);
        orderDetailResponseDto.setBookId(1L);

        List<OrderDetailResponseDto> orderDetailResponseDtos = new ArrayList<>();
        orderDetailResponseDtos.add(orderDetailResponseDto);
        orderResponseDto.setOrderDetails(orderDetailResponseDtos);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderDetailRepository.findByOrderId(orderId)).thenReturn(orderDetails);
        when(orderMapper.mapOrderDetailListToOrderDetailResponseList(orderDetails)).thenReturn(orderDetailResponseDtos);

        OrderResponseDto result = orderService.getOrderById(orderId);

        assertEquals(orderResponseDto.getOrderDetails().size(), result.getOrderDetails().size());
        assertEquals(orderResponseDto.getOrderDetails().get(0).getOrderDetailId(), result.getOrderDetails().get(0).getOrderDetailId());
        assertEquals(orderResponseDto.getOrderDetails().get(0).getBookId(), result.getOrderDetails().get(0).getBookId());
    }

    @Test
    void testGetOrderById() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(1L);
        orderDetail.setBookId(1L);
        orderDetail.setCount(1);
        orderDetail.setTotalAmount(BigDecimal.TEN);
        OrderDetailResponseDto orderDetailResponseDto = new OrderDetailResponseDto();
        orderDetailResponseDto.setOrderId(1L);
        orderDetailResponseDto.setCustomerId(1L);
        orderDetailResponseDto.setOrderDetailId(1L);
        orderDetailResponseDto.setBookId(1L);
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setStatus(OrderStatusEnum.COMPLETED.getStatus());
        orderResponseDto.setOrderDetails(List.of(orderDetailResponseDto));
        Order order = new Order();
        order.setOrderId(1L);
        order.setStatus(OrderStatusEnum.COMPLETED.getStatus());

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderDetailRepository.findByOrderId(anyLong())).thenReturn(List.of(orderDetail));
        when(orderMapper.mapOrderDetailListToOrderDetailResponseList(any())).thenReturn(List.of(orderDetailResponseDto));
        OrderResponseDto result = orderService.getOrderById(1L);
        assertEquals(OrderStatusEnum.COMPLETED.getStatus(), result.getStatus());
        assertEquals(1, result.getOrderDetails().size());
        assertEquals(1L, result.getOrderDetails().get(0).getOrderId());
        assertEquals(1L, result.getOrderDetails().get(0).getBookId());
    }

}
