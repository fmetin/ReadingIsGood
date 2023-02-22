package com.fmetin.readingisgood.service;

import com.fmetin.readingisgood.dto.StatisticsResponseDto;
import com.fmetin.readingisgood.entity.Order;
import com.fmetin.readingisgood.enums.OrderStatusEnum;
import com.fmetin.readingisgood.repository.OrderRepository;
import com.fmetin.readingisgood.service.impl.StatisticsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class StatisticsServiceTest {

    private StatisticsServiceImpl statisticsService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        statisticsService = new StatisticsServiceImpl(orderRepository);
    }

    @Test
    public void getCustomerStatistics_Success() {
        long customerId = 1L;
        LocalDateTime now = LocalDateTime.now();
        List<Order> orderList = new ArrayList<>();
        Order order1 = new Order();
        order1.setOrderId(1L);
        order1.setCustomerId(customerId);
        order1.setStatus(OrderStatusEnum.COMPLETED.getStatus());
        order1.setTotalAmount(new BigDecimal(100));
        order1.setCountOfItems(2);
        order1.setCreatedDate(now);
        orderList.add(order1);


        Order order4 = new Order();
        order4.setOrderId(4L);
        order4.setCustomerId(customerId);
        order4.setStatus(OrderStatusEnum.COMPLETED.getStatus());
        order4.setTotalAmount(new BigDecimal(150));
        order4.setCountOfItems(3);
        order4.setCreatedDate(now);
        orderList.add(order4);

        Order order2 = new Order();
        order2.setOrderId(2L);
        order2.setCustomerId(customerId);
        order2.setStatus(OrderStatusEnum.COMPLETED.getStatus());
        order2.setTotalAmount(new BigDecimal(50));
        order2.setCountOfItems(1);
        order2.setCreatedDate(now.minusMonths(1));
        orderList.add(order2);

        Order order3 = new Order();
        order3.setOrderId(3L);
        order3.setCustomerId(customerId);
        order3.setStatus(OrderStatusEnum.COMPLETED.getStatus());
        order3.setTotalAmount(new BigDecimal(200));
        order3.setCountOfItems(5);
        order3.setCreatedDate(now.minusMonths(1));
        orderList.add(order3);


        when(orderRepository.findByCustomerIdAndStatusOrderByCreatedDateDesc(customerId, OrderStatusEnum.COMPLETED.getStatus())).thenReturn(orderList);

        List<StatisticsResponseDto> result = statisticsService.getCustomerStatistics(customerId);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("FEBRUARY", result.get(0).getMonth());
        Assertions.assertEquals(2, result.get(0).getTotalOrderCount());
        Assertions.assertEquals(5, result.get(0).getTotalBookCount());
        Assertions.assertEquals(new BigDecimal(250).setScale(2), result.get(0).getTotalPurchasedAmount());

        Assertions.assertEquals("JANUARY", result.get(1).getMonth());
        Assertions.assertEquals(2, result.get(1).getTotalOrderCount());
        Assertions.assertEquals(6, result.get(1).getTotalBookCount());
        Assertions.assertEquals(new BigDecimal(250).setScale(2), result.get(1).getTotalPurchasedAmount());
    }

}
