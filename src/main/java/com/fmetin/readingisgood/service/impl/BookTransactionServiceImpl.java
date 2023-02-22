package com.fmetin.readingisgood.service.impl;

import com.fmetin.readingisgood.dto.UpdateBookStocksRequestDto;
import com.fmetin.readingisgood.locker.DistributedLocker;
import com.fmetin.readingisgood.locker.LockExecutionResult;
import com.fmetin.readingisgood.service.BookService;
import com.fmetin.readingisgood.service.BookTransactionService;
import com.fmetin.readingisgood.shared.CommonProperties;
import com.fmetin.readingisgood.shared.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BookTransactionServiceImpl implements BookTransactionService {
    private final BookService bookService;

    private final DistributedLocker locker;

    @Autowired
    public BookTransactionServiceImpl(BookService bookService, DistributedLocker locker) {
        this.bookService = bookService;
        this.locker = locker;
    }

    @Override
    @Transactional
    public void updateBookStocks(UpdateBookStocksRequestDto request) {
        try {
            String key = "bookdId:" + request.getBookId();
            LockExecutionResult<String> result = locker.lock(key, CommonProperties.REDIS_LOCK_ACQUIRED_SECONDS, CommonProperties.REDIS_LOCK_TIMEOUT_SECONDS, () -> {
                bookService.updateBookStocks(request);
                return null;
            });
            if (result.hasException())
                throw (RestException) result.exception;
        } catch (Exception e) {
            log.error("Update book stocks error!", e);
            throw e;
        }
    }
}
