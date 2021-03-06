package com.mathsystem.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {
            SqlConflictException.class,
            SqlNotFoundException.class
    })
    public ResponseEntity<?> handleDataConflictException(SqlConflictException exception, WebRequest request) {
        log.error(exception.getMessage());
        log.error("With code - %s".formatted(exception.getErrorCode().toString()));
        return handleExceptionInternal(
                exception,
                new ErrorResponseBody(exception.getMessage(), exception.getErrorCode().toString()),
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                request);
    }

    private record ErrorResponseBody(String message, String code) {}
}
