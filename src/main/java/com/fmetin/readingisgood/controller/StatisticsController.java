package com.fmetin.readingisgood.controller;

import com.fmetin.readingisgood.annotation.CurrentUser;
import com.fmetin.readingisgood.conf.CustomerUserDetails;
import com.fmetin.readingisgood.service.StatisticsService;
import com.fmetin.readingisgood.shared.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/v1/customer-statistics")
    public ResponseEntity<Object> getCustomerStatistics(@CurrentUser CustomerUserDetails customerUserDetails) {

        return ResponseEntity.ok(new RestResponse<>(
                statisticsService.getCustomerStatistics(
                        customerUserDetails.getUser().getCustomerId()))
        );
    }
}
