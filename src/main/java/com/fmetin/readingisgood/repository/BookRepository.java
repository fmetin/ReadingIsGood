package com.fmetin.readingisgood.repository;

import com.fmetin.readingisgood.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    long countByName(String name);


}
