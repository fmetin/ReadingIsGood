package com.fmetin.readingisgood.service;

import com.fmetin.readingisgood.dto.CreateCustomerRequestDto;
import com.fmetin.readingisgood.entity.Customer;
import com.fmetin.readingisgood.mapper.CustomerMapper;
import com.fmetin.readingisgood.repository.CustomerRepository;
import com.fmetin.readingisgood.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    @Test
    public void testCreateCustomer() {
        // Given
        CreateCustomerRequestDto request = new CreateCustomerRequestDto();
        request.setName("John");
        request.setSurname("Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("Password1!");

        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setSurname(request.getSurname());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());

        when(customerMapper.mapCustomerToCreateCustomerRequestDto(request)).thenReturn(customer);

        // When
        customerService.createCustomer(request);

        // Then
        verify(customerRepository).save(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertNotNull(capturedCustomer);
        assertEquals(request.getName(), capturedCustomer.getName());
        assertEquals(request.getSurname(), capturedCustomer.getSurname());
        assertEquals(request.getEmail(), capturedCustomer.getEmail());
        assertEquals(request.getPassword(), capturedCustomer.getPassword());
    }

}
