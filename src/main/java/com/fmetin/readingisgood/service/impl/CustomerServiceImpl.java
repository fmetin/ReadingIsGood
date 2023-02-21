package com.fmetin.readingisgood.service.impl;

import com.fmetin.readingisgood.dto.CreateCustomerRequestDto;
import com.fmetin.readingisgood.entity.Customer;
import com.fmetin.readingisgood.mapper.CustomerMapper;
import com.fmetin.readingisgood.repository.CustomerRepository;
import com.fmetin.readingisgood.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public void createCustomer(CreateCustomerRequestDto request) {
        Customer customer = customerMapper.mapCustomerToCreateCustomerRequestDto(request);
        customerRepository.save(customer);
    }

    @Override
    public long countByEmail(String email) {
        return customerRepository.countByEmail(email);
    }
}
