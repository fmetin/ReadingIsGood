package com.fmetin.readingisgood.controller;

import com.fmetin.readingisgood.annotation.CurrentUser;
import com.fmetin.readingisgood.conf.CustomerUserDetails;
import com.fmetin.readingisgood.dto.CreateCustomerRequestDto;
import com.fmetin.readingisgood.service.CustomerService;
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
public class CustomerController {
    private final CustomerService customerService;
    private final OrderService orderService;

    @Autowired
    public CustomerController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @PostMapping("/v1/create-customer")
    public ResponseEntity<RestResponse<?>> createCustomer(@Valid @RequestBody CreateCustomerRequestDto request) {
        customerService.createCustomer(request);
        return ResponseEntity.ok(new RestResponse<>());
    }

    @GetMapping("/v1/customer-orders")
    public ResponseEntity<?> getCustomerOrders(
            @CurrentUser CustomerUserDetails customerUserDetails,
            @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(new RestResponse<>(
                orderService.getCustomerOrders(
                        customerUserDetails.getUser().getCustomerId()
                        , pageable))
        );
    }
}
