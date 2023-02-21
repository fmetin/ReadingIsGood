package com.fmetin.readingisgood.controller;

import com.fmetin.readingisgood.annotation.CurrentUser;
import com.fmetin.readingisgood.conf.CustomerUserDetails;
import com.fmetin.readingisgood.dto.OrderListByDateRequestDto;
import com.fmetin.readingisgood.dto.OrderRequestDto;
import com.fmetin.readingisgood.service.OrderService;
import com.fmetin.readingisgood.service.OrderTransactionService;
import com.fmetin.readingisgood.shared.RestResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private final OrderService orderService;
    private final OrderTransactionService orderTransactionService;

    @Autowired
    public OrderController(OrderService orderService, OrderTransactionService orderTransactionService) {
        this.orderService = orderService;
        this.orderTransactionService = orderTransactionService;
    }

    @PostMapping("/v1/order")
    public ResponseEntity<RestResponse<Object>> order(@Valid @RequestBody OrderRequestDto request,
                                                      @CurrentUser CustomerUserDetails customerUserDetails) {
        request.setCustomerId(customerUserDetails.getUser().getCustomerId());
        orderTransactionService.order(request);
        return ResponseEntity.ok(new RestResponse<>());
    }

    @GetMapping("/v1/order/{orderId}")
    @PreAuthorize("@customerSecurity.isAllowedToAccess(#orderId, principal.user)")
    public ResponseEntity<Object> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(new RestResponse<>(orderService.getOrderById(orderId)));
    }

    @PostMapping("/v1/order-list-by-date")
    public ResponseEntity<RestResponse<Object>> orderListByDate(@Valid @RequestBody OrderListByDateRequestDto request,
                                                                @CurrentUser CustomerUserDetails customerUserDetails) {
        return ResponseEntity.ok(new RestResponse<>(orderService.orderListByDate(request, customerUserDetails.getUser().getCustomerId())));
    }
}
