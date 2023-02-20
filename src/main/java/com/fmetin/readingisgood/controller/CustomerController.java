package com.fmetin.readingisgood.controller;

import com.fmetin.readingisgood.dto.CreateCustomerRequestDto;
import com.fmetin.readingisgood.service.CustomerService;
import com.fmetin.readingisgood.shared.RestResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/v1/create-customer")
    public ResponseEntity<RestResponse<?>> createCustomer(@Valid @RequestBody CreateCustomerRequestDto request) {
        customerService.createCustomer(request);
        return ResponseEntity.ok(new RestResponse<>());
    }
}
