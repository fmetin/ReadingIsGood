package com.fmetin.readingisgood.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static com.fmetin.readingisgood.shared.RestResponseMessage.MSG_VALIDATION_CONSTRAINT_BOOK_ID_NOTNULL;
import static com.fmetin.readingisgood.shared.RestResponseMessage.MSG_VALIDATION_CONSTRAINT_STOCK_NOTNULL;

@Data
public class UpdateBookStocksRequestDto {

    @NotNull(message = MSG_VALIDATION_CONSTRAINT_BOOK_ID_NOTNULL)
    private Long bookId;
    @NotNull(message = MSG_VALIDATION_CONSTRAINT_STOCK_NOTNULL)
    @Min(0)
    private Integer stock;
}
