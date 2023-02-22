package com.fmetin.readingisgood.service;

import com.fmetin.readingisgood.dto.CreateBookRequestDto;
import com.fmetin.readingisgood.entity.Book;
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

import static com.fmetin.readingisgood.shared.RestResponseCode.BOOK_NOT_FOUND;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create Book Test")
    void createBook() {
        CreateBookRequestDto request = new CreateBookRequestDto();
        request.setName("Test Book");
        request.setPrice(BigDecimal.TEN);
        request.setStock(100);

        Book book = new Book();
        book.setName(request.getName());
        book.setPrice(request.getPrice());
        book.setStock(request.getStock());

        Mockito.when(bookMapper.mapCreateBookRequestDtoToBook(request)).thenReturn(book);

        bookService.createBook(request);

        Mockito.verify(bookRepository).save(book);
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

        RestException exception = Assertions.assertThrows(RestException.class, () -> bookService.findByBookId(bookId));

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

       
