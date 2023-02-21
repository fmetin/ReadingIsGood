package com.fmetin.readingisgood.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static com.fmetin.readingisgood.shared.RestResponseMessage.MSG_VALIDATION_CONSTRAINT_BOOK_ID_NOTNULL;

@Data
public class OrderDetailsDto {
    @NotNull(message = MSG_VALIDATION_CONSTRAINT_BOOK_ID_NOTNULL)
    private Long bookId;
    @Min(1)
    private int count;
}
