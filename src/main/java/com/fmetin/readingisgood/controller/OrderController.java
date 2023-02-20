package com.fmetin.readingisgood.controller;

import com.fmetin.readingisgood.dto.CreateBookRequestDto;
import com.fmetin.readingisgood.dto.OrderListByDateRequestDto;
import com.fmetin.readingisgood.dto.OrderRequestDto;
import com.fmetin.readingisgood.service.OrderService;
import com.fmetin.readingisgood.shared.RestResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/v1/order")
    public ResponseEntity<RestResponse<?>> order(@Valid @RequestBody OrderRequestDto request) {
        //todo get customerId from security context
        request.setCustomerId(1L);
        orderService.order(request);
        return ResponseEntity.ok(new RestResponse<>());
    }

    @GetMapping("/v1/order/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        //todo check customerId to get order response
        return ResponseEntity.ok(new RestResponse<>(orderService.getOrderById(orderId)));
    }

    @PostMapping("/v1/order-list-by-date")
    public ResponseEntity<RestResponse<?>> orderListByDate(@Valid @RequestBody OrderListByDateRequestDto request) {
        //todo check customerId to get order response
        return ResponseEntity.ok(new RestResponse<>(orderService.orderListByDate(request, 1L)));
    }
}
