package com.fmetin.readingisgood.service;

import com.fmetin.readingisgood.dto.CreateBookRequestDto;
import com.fmetin.readingisgood.entity.Book;
import com.fmetin.readingisgood.locker.DistributedLocker;
import com.fmetin.readingisgood.locker.LockExecutionResult;
import com.fmetin.readingisgood.mapper.BookMapper;
import com.fmetin.readingisgood.repository.BookRepository;
import com.fmetin.readingisgood.service.impl.BookServiceImpl;
import com.fmetin.readingisgood.shared.RestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static com.fmetin.readingisgood.shared.RestResponseCode.BOOK_NOT_FOUND;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private DistributedLocker locker;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBook_shouldSaveBook_whenLockingIsSuccessful() {
        // Arrange
        CreateBookRequestDto request = new CreateBookRequestDto();
        request.setName("bookName");
        request.setStock(10);
        request.setPrice(BigDecimal.TEN);

        when(bookMapper.mapCreateBookRequestDtoToBook(any(CreateBookRequestDto.class))).thenReturn(new Book());
        when(locker.lock(anyString(), anyInt(), anyInt(), any())).thenReturn(LockExecutionResult.buildLockAcquiredResult(null));

        // Act
        bookService.createBook(request);

        // Assert
        verify(bookRepository, times(0)).save(any(Book.class));
    }

    @Test
    void createBook_shouldThrowRestException_whenLockingFails() {
        // Arrange
        CreateBookRequestDto request = new CreateBookRequestDto();
        request.setName("bookName");
        request.setStock(10);
        request.setPrice(BigDecimal.TEN);

        when(locker.lock(anyString(), anyInt(), anyInt(), any())).thenReturn(LockExecutionResult.buildLockAcquiredWithException(new RestException(BOOK_NOT_FOUND)));

        // Act and Assert
        assertThrows(Exception.class, () -> bookService.createBook(request));
    }

    @Test
    @DisplayName("Count Book By Name Test")
    void countByName() {
        String name = "Test Book";
        Mockito.when(bookRepository.countByName(name)).thenReturn(5L);

        long result = bookService.countByName(name);

        Assertions.assertEquals(5L, result);
    }

    @Test
    @DisplayName("Find Book By Id Test - Book Found")
    void findByBookId_bookFound() {
        long bookId = 1L;

        Book book = new Book();
        book.setBookId(bookId);

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Book result = bookService.findByBookId(bookId);

        Assertions.assertEquals(book, result);
    }

    @Test
    @DisplayName("Find Book By Id Test - Book Not Found")
    void findByBookId_bookNotFound() {
        long bookId = 1L;

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        RestException exception = assertThrows(RestException.class, () -> bookService.findByBookId(bookId));

        Assertions.assertEquals(BOOK_NOT_FOUND.getResponseCode(), exception.getResponseCode());
    }

    @Test
    @DisplayName("Find Book Price By Id Test")
    void findByBookIdToGetPrice() {
        long bookId = 1L;

        BigDecimal expectedPrice = BigDecimal.TEN;

        Mockito.when(bookRepository.findByBookIdToGetPrice(bookId)).thenReturn(expectedPrice);

        BigDecimal result = bookService.findByBookIdToGetPrice(bookId);

        Assertions.assertEquals(expectedPrice, result);
    }

    @Test
    @DisplayName("Find Book Stock By Id Test")
    void findByBookIdToGetStock() {
        long bookId = 1L;

        int expectedStock = 100;

        Mockito.when(bookRepository.findByBookIdToGetStock(bookId)).thenReturn(expectedStock);

        int result = bookRepository.findByBookIdToGetStock(bookId);

        Assertions.assertEquals(expectedStock, result);
    }
}

       
