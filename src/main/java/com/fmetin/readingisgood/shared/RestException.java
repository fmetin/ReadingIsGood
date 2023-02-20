package com.fmetin.readingisgood.shared;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class RestException extends RuntimeException {
    private String responseCode;
    private String responseMessage;
    private HttpStatus httpStatus = HttpStatus.OK;

    public RestException(RestResponseCode restResponseCode, HttpStatus httpStatus) {
        super(restResponseCode.getResponseMessage());
        this.responseCode = restResponseCode.getResponseCode();
        this.responseMessage = restResponseCode.getResponseMessage();
        this.httpStatus = httpStatus;
    }

    public RestException(RestResponseCode restResponseCode) {
        super(restResponseCode.getResponseMessage());
        this.responseCode = restResponseCode.getResponseCode();
        this.responseMessage = restResponseCode.getResponseMessage();
    }

    public String getResponseMessage() {
        return Translator.toLocale(responseMessage);
    }


}
