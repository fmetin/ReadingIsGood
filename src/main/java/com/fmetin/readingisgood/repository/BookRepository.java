package com.fmetin.readingisgood.repository;

import com.fmetin.readingisgood.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface BookRepository extends JpaRepository<Book, Long> {
    long countByName(String name);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Modifying
    @Query("update Book b set b.stock = ?1, b.updatedDate = ?2 where b.bookId = ?3")
    void updateStockAndUpdatedDateByBookId(int stock, LocalDateTime updatedDate, long bookId);






}
