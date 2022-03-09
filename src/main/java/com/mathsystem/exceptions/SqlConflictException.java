package com.mathsystem.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SqlConflictException extends RuntimeException {
    private final String message;
    private final ErrorCode errorCode;
}
