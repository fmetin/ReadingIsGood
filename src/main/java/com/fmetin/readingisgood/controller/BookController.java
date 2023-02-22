package com.fmetin.readingisgood.controller;

import com.fmetin.readingisgood.dto.CreateBookRequestDto;
import com.fmetin.readingisgood.dto.UpdateBookStocksRequestDto;
import com.fmetin.readingisgood.service.BookService;
import com.fmetin.readingisgood.service.BookTransactionService;
import com.fmetin.readingisgood.shared.RestResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final BookService bookService;
    private final BookTransactionService bookTransactionService;

    @Autowired
    public BookController(BookService bookService, BookTransactionService bookTransactionService) {
        this.bookService = bookService;
        this.bookTransactionService = bookTransactionService;
    }

    @PostMapping("/v1/create-book")
    public ResponseEntity<RestResponse<Object>> createBook(@Valid @RequestBody CreateBookRequestDto request) {
        bookService.createBook(request);
        return ResponseEntity.ok(new RestResponse<>());
    }

    @PostMapping("/v1/update-book-stocks")
    public ResponseEntity<RestResponse<Object>> updateBookStocks(@Valid @RequestBody UpdateBookStocksRequestDto request) {
        bookTransactionService.updateBookStocks(request);
        return ResponseEntity.ok(new RestResponse<>());
    }
}
