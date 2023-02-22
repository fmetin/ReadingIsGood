package com.fmetin.readingisgood.repository;

import com.fmetin.readingisgood.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Transactional(readOnly = true)
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b.price from Book b where b.bookId = ?1")
    BigDecimal findByBookIdToGetPrice(long bookId);
    @Query("select b.stock from Book b where b.bookId = ?1")
    int findByBookIdToGetStock(long bookId);
    long countByName(String name);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Book b set b.stock = ?1, b.updatedDate = ?2 where b.bookId = ?3")
    int updateStockAndUpdatedDateByBookId(int stock, LocalDateTime updatedDate, long bookId);






}
