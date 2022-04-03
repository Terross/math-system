package com.mathsystem.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DataException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;
}
