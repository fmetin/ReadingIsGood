package com.fmetin.readingisgood.service;

import com.fmetin.readingisgood.dto.StatisticsResponseDto;

import java.util.List;

public interface StatisticsService {
    List<StatisticsResponseDto> getCustomerStatistics(long customerId);
}
