package com.fmetin.readingisgood.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

import static com.fmetin.readingisgood.shared.RestResponseMessage.*;

@Data
public class OrderRequestDto {

    @NotNull(message = MSG_VALIDATION_CONSTRAINT_ORDER_LIST_NOTNULL)
    @NotEmpty(message = MSG_VALIDATION_CONSTRAINT_ORDER_LIST_NOTEMPTY)
    @Valid
    private List<OrderDetailsDto> orderList;

    private Long customerId;
}
