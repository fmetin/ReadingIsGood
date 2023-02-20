package com.fmetin.readingisgood.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {

    IN_PROGRESS(0),
    COMPLETED(1),
    FAILED(2),
    ;

    private final int status;

    OrderStatusEnum(int status) {
        this.status = status;
    }

}
