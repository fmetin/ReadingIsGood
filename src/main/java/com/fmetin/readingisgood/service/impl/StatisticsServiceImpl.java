package com.fmetin.readingisgood.service.impl;

import com.fmetin.readingisgood.dto.StatisticsResponseDto;
import com.fmetin.readingisgood.entity.Order;
import com.fmetin.readingisgood.enums.OrderStatusEnum;
import com.fmetin.readingisgood.repository.OrderRepository;
import com.fmetin.readingisgood.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {

    private final OrderRepository orderRepository;

    @Autowired
    public StatisticsServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<StatisticsResponseDto> getCustomerStatistics(long customerId) {
        List<StatisticsResponseDto> statisticsResponseDtoList = new ArrayList<>();
        List<Order> orderList = orderRepository.
                findByCustomerIdAndStatusOrderByCreatedDateDesc(
                        customerId,
                        OrderStatusEnum.COMPLETED.getStatus());
        //group by month
        Collection<List<Order>> values = orderList.
                stream().
                collect(
                        Collectors.groupingBy(order -> order.getCreatedDate().getMonth(),
                                LinkedHashMap::new,
                                Collectors.toList()
                        )).values();

        //calculate statistics
        values.forEach(orders -> {
            LocalDateTime orderDate = orders.get(0).getCreatedDate();

            StatisticsResponseDto statisticsResponseDto = new StatisticsResponseDto();
            statisticsResponseDto.setMonth(orderDate.getMonth().toString());
            statisticsResponseDto.setTotalOrderCount(orders.size());
            statisticsResponseDto.setTotalBookCount(orders.stream().mapToInt(Order::getCountOfItems).sum());
            BigDecimal totalPurchasedAmount = BigDecimal.valueOf(orders.stream().mapToDouble(value -> value.getTotalAmount().doubleValue()).sum());
            statisticsResponseDto.setTotalPurchasedAmount(totalPurchasedAmount.setScale(2, RoundingMode.HALF_DOWN));
            statisticsResponseDtoList.add(statisticsResponseDto);
        });

        return statisticsResponseDtoList;
    }
}
