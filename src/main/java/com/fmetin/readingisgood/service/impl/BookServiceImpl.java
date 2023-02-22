package com.fmetin.readingisgood.service.impl;

import com.fmetin.readingisgood.dto.CreateBookRequestDto;
import com.fmetin.readingisgood.dto.UpdateBookStocksRequestDto;
import com.fmetin.readingisgood.entity.Book;
import com.fmetin.readingisgood.mapper.BookMapper;
import com.fmetin.readingisgood.repository.BookRepository;
import com.fmetin.readingisgood.service.BookService;
import com.fmetin.readingisgood.shared.RestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.fmetin.readingisgood.shared.RestResponseCode.BOOK_NOT_FOUND;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public void createBook(CreateBookRequestDto request) {
        Book book = bookMapper.mapCreateBookRequestDtoToBook(request);
        bookRepository.save(book);
    }

    @Override
    public long countByName(String name) {
        return bookRepository.countByName(name);
    }

    @Override
    public Book findByBookId(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty())
            throw new RestException(BOOK_NOT_FOUND);
        return optionalBook.get();
    }

    @Override
    public BigDecimal findByBookIdToGetPrice(Long bookId) {
        BigDecimal price = bookRepository.findByBookIdToGetPrice(bookId);
        if (price == null)
            throw new RestException(BOOK_NOT_FOUND);
        return price;
    }

    @Override
    public int findByBookIdToGetStock(Long bookId) {
        return bookRepository.findByBookIdToGetStock(bookId);
    }

    @Override
    public void updateBookStocks(UpdateBookStocksRequestDto request) {
        int result = bookRepository.updateStockAndUpdatedDateByBookId(request.getStock(), LocalDateTime.now(), request.getBookId());
        if (result == 0)
            throw new RestException(BOOK_NOT_FOUND);

    }
}
