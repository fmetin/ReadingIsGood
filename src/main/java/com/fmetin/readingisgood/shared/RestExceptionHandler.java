package com.fmetin.readingisgood.shared;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import static com.fmetin.readingisgood.shared.RestResponseCode.*;


@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(InsufficientAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public RestResponse<Void> handleInsufficientAuthenticationException(InsufficientAuthenticationException e) {
        return new RestResponse<>(
                new RestResponseHeader(UNAUTHORIZED.getResponseCode(),
                        UNAUTHORIZED.getlocalizedResponseMessage()),
                null, null
        );
    }
    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public RestResponse<Void> handleAccessDeniedException(AccessDeniedException e) {
        return new RestResponse<>(
                new RestResponseHeader(FORBIDDEN_ERROR.getResponseCode(),
                        FORBIDDEN_ERROR.getlocalizedResponseMessage()),
                null, null
        );
    }

    @ExceptionHandler({org.springframework.security.access.AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public RestResponse<Void> handleAccessDeniedException(org.springframework.security.access.AccessDeniedException e) {
        return new RestResponse<>(
                new RestResponseHeader(FORBIDDEN_ERROR.getResponseCode(),
                        FORBIDDEN_ERROR.getlocalizedResponseMessage()),
                null, null
        );
    }
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public RestResponse<Void> handleAuthenticationException(AuthenticationException e) {
        return new RestResponse<>(
                new RestResponseHeader(BAD_CREDENTIAL.getResponseCode(),
                        BAD_CREDENTIAL.getlocalizedResponseMessage()),
                null, null
        );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError :
                e.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new RestResponse<>(
                new RestResponseHeader(VALIDATION_ERROR.getResponseCode(),
                        VALIDATION_ERROR.getlocalizedResponseMessage()),
                null, validationErrors
        );
    }

    @ExceptionHandler(RestException.class)
    @ResponseBody
    public ResponseEntity<RestResponse<Void>> handleRestException(RestException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new RestResponse<>(
                new RestResponseHeader(e.getResponseCode(),
                        e.getResponseMessage()),
                null, null
        ));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RestResponse<Void> handleException(Exception e) {
        return new RestResponse<>(
                new RestResponseHeader(UNKNOWN_ERROR.getResponseCode(),
                        UNKNOWN_ERROR.getlocalizedResponseMessage()),
                null, null);
    }
}