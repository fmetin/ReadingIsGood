package com.fmetin.readingisgood.service;

import com.fmetin.readingisgood.dto.UpdateBookStocksRequestDto;

public interface BookTransactionService {

    void updateBookStocks(UpdateBookStocksRequestDto request);
}
