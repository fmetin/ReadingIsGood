package com.fmetin.readingisgood.repository;

import com.fmetin.readingisgood.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerIdAndCreatedDateBetween(long customerId, LocalDateTime createdDateStart, LocalDateTime createdDateEnd);
}
