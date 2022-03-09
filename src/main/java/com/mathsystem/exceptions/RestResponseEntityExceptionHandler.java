package com.mathsystem.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = SqlConflictException.class)
    public ResponseEntity<?> handleDataConflictException(SqlConflictException exception, WebRequest request) {
        return handleExceptionInternal(
                exception,
                new ErrorResponseBody(exception.getMessage(), exception.getErrorCode().toString()),
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                request);
    }

    private record ErrorResponseBody(String message, String code) {}
}
