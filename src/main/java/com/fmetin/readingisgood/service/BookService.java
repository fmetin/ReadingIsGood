package com.fmetin.readingisgood.service;

import com.fmetin.readingisgood.dto.CreateBookRequestDto;
import com.fmetin.readingisgood.dto.UpdateBookStocksRequestDto;
import com.fmetin.readingisgood.entity.Book;

public interface BookService {
    void createBook(CreateBookRequestDto request);

    long countByName(String name);
    Book findByBookId(Long bookId);

    void updateBookStocks(UpdateBookStocksRequestDto request);
}
