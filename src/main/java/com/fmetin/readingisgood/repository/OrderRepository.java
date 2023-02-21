package com.fmetin.readingisgood.repository;

import com.fmetin.readingisgood.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerIdAndCreatedDateBetween(long customerId, LocalDateTime createdDateStart, LocalDateTime createdDateEnd);

    @Query("select o from Order o where o.customerId = :customerId")
    Page<Order> findByCustomerId(@Param("customerId") long customerId, Pageable pageable);


}
