package com.fmetin.readingisgood.repository;

import com.fmetin.readingisgood.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
