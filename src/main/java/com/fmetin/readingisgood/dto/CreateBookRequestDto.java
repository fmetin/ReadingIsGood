package com.fmetin.readingisgood.dto;

import com.fmetin.readingisgood.annotation.UniqueBookName;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

import static com.fmetin.readingisgood.shared.RestResponseMessage.*;

@Data
public class CreateBookRequestDto {
    @NotNull(message = MSG_VALIDATION_CONSTRAINT_NAME_NOTNULL)
    @UniqueBookName
    @Size(min = 1, max = 255, message = MSG_VALIDATION_CONSTRAINT_NAME_SIZE)
    private String name;
    @NotNull(message = MSG_VALIDATION_CONSTRAINT_PRICE_NOTNULL)
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 3, fraction = 2)
    private BigDecimal price;

    @NotNull(message = MSG_VALIDATION_CONSTRAINT_STOCK_NOTNULL)
    @Min(0)
    private Integer stock;
}
