package com.fmetin.readingisgood.shared;


import lombok.AllArgsConstructor;
import lombok.Data;

import static com.fmetin.readingisgood.shared.RestResponseMessage.*;

@Data
@AllArgsConstructor
public class RestResponseCode {

    private String responseCode;
    private String responseMessage;
    public static final RestResponseCode BAD_CREDENTIAL = new RestResponseCode("GNL-0001", MSG_BAD_CREDENTIAL);
    public static final RestResponseCode VALIDATION_ERROR = new RestResponseCode("GNL-0002", MSG_VALIDATION_ERROR);
    public static final RestResponseCode FORBIDDEN_ERROR = new RestResponseCode("GNL-0003", MSG_FORBIDDEN_ERROR);
    public static final RestResponseCode BOOK_NOT_FOUND = new RestResponseCode("GNL-0004", MSG_BOOK_NOT_FOUND);
    public static final RestResponseCode THERE_IS_NOT_STOCK = new RestResponseCode("GNL-0005", MSG_THERE_IS_NOT_STOCK);
    public static final RestResponseCode UNKNOWN_ERROR = new RestResponseCode("GNL-0006", MSG_UNKNOWN_ERROR);
    public static final RestResponseCode ORDER_NOT_FOUND = new RestResponseCode("GNL-0007", MSG_ORDER_NOT_FOUND);
    public static final RestResponseCode REDIS_LOCK_ERROR = new RestResponseCode("GNL-0008", MSG_REDIS_LOCK_ERROR);
    public static final RestResponseCode TRANSACTION_TIMEOUT = new RestResponseCode("GNL-0009", MSG_TRANSACTION_TIMEOUT);
    public String getlocalizedResponseMessage() {
        return Translator.toLocale(responseMessage);
    }
}
