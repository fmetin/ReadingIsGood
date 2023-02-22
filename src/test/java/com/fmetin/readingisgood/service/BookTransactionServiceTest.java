package com.fmetin.readingisgood.service;

import com.fmetin.readingisgood.dto.UpdateBookStocksRequestDto;
import com.fmetin.readingisgood.locker.DistributedLocker;
import com.fmetin.readingisgood.locker.LockExecutionResult;
import com.fmetin.readingisgood.service.impl.BookTransactionServiceImpl;
import com.fmetin.readingisgood.shared.CommonProperties;
import com.fmetin.readingisgood.shared.RestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.concurrent.Callable;

import static com.fmetin.readingisgood.shared.RestResponseCode.BOOK_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BookTransactionServiceTest {
    @Mock
    private BookService bookService;

    @Mock
    private DistributedLocker locker;

    @InjectMocks
    private BookTransactionServiceImpl bookTransactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateBookStocks() throws Exception {
        UpdateBookStocksRequestDto request = new UpdateBookStocksRequestDto();
        request.setBookId(1L);
        request.setStock(1);

        when(locker.lock(anyString(), anyInt(), anyInt(), any(Callable.class)))
                .thenAnswer((Answer<LockExecutionResult<?>>) invocation -> {
                    Callable<?> task = invocation.getArgument(3);
                    Object result = task.call();
                    return LockExecutionResult.buildLockAcquiredResult(result);
                });

        bookTransactionService.updateBookStocks(request);

        verify(bookService, times(1)).updateBookStocks(request);
        verify(locker, times(1)).lock(anyString(), eq(CommonProperties.REDIS_LOCK_ACQUIRED_SECONDS), eq(CommonProperties.REDIS_LOCK_TIMEOUT_SECONDS), any(Callable.class));
    }

    @Test
    void testUpdateBookStocksWithException() throws Exception {
        UpdateBookStocksRequestDto request = new UpdateBookStocksRequestDto();
        request.setBookId(1L);
        request.setStock(1);

        RestException exception = new RestException(BOOK_NOT_FOUND);

        when(locker.lock(anyString(), anyInt(), anyInt(), any(Callable.class)))
                .thenAnswer((Answer<LockExecutionResult<?>>) invocation -> {
                    Callable<?> task = invocation.getArgument(3);
                    throw exception;
                });

        RestException thrownException = assertThrows(RestException.class,
                () -> bookTransactionService.updateBookStocks(request),
                "Expected updateBookStocks to throw, but it didn't");

        assertEquals(exception, thrownException);

        verify(bookService, never()).updateBookStocks(request);
        verify(locker, times(1)).lock(anyString(), eq(CommonProperties.REDIS_LOCK_ACQUIRED_SECONDS), eq(CommonProperties.REDIS_LOCK_TIMEOUT_SECONDS), any(Callable.class));
    }
}
