package com.fmetin.readingisgood.service;

import com.fmetin.readingisgood.entity.Customer;
import com.fmetin.readingisgood.entity.Order;
import com.fmetin.readingisgood.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "customerSecurity")
public class CustomerSecurityService {

    private final OrderRepository orderRepository;

    @Autowired
    public CustomerSecurityService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public boolean isAllowedToAccess(long id, Customer loggedInUser) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty())
            return false;
        Order order = optionalOrder.get();
        return order.getCustomerId() == loggedInUser.getCustomerId();
    }
}
