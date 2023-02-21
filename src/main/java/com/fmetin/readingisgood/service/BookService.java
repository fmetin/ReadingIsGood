package com.fmetin.readingisgood.service;

import com.fmetin.readingisgood.dto.CreateBookRequestDto;
import com.fmetin.readingisgood.dto.UpdateBookStocksRequestDto;
import com.fmetin.readingisgood.entity.Book;

import java.math.BigDecimal;

public interface BookService {
    void createBook(CreateBookRequestDto request);

    long countByName(String name);
    Book findByBookId(Long bookId);
    BigDecimal findByBookIdToGetPrice(Long bookId);
    int findByBookIdToGetStock(Long bookId);

    void updateBookStocks(UpdateBookStocksRequestDto request);
}
