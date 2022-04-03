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
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException exception, WebRequest request) {
        log.error(exception.getMessage());
        log.error("With code - %s".formatted(exception.getErrorCode().name()));
        return handleExceptionInternal(
                exception,
                new ErrorResponseBody(exception.getMessage(), exception.getErrorCode().name()),
                new HttpHeaders(),
                HttpStatus.UNPROCESSABLE_ENTITY,
                request);
    }

    @ExceptionHandler(DataException.class)
    public ResponseEntity<?> handleDataException(DataException exception, WebRequest request) {
        log.error(exception.getMessage());
        log.error("With code - %s".formatted(exception.getErrorCode().name()));
        return handleExceptionInternal(
                exception,
                new ErrorResponseBody(exception.getMessage(), exception.getErrorCode().name()),
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                request);
    }

    private record ErrorResponseBody(String message, String code) {}
}
