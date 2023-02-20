package com.fmetin.readingisgood.service;

import com.fmetin.readingisgood.dto.CreateCustomerRequestDto;

public interface CustomerService {
    void createCustomer(CreateCustomerRequestDto request);
}
