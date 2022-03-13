package com.mathsystem.exceptions;

public enum ErrorCode {
    USER_ALREADY_EXIST("0", "userAlreadyExist"),
    USER_NOT_FOUND("1", "userNotFound"),

    NOT_ENOUGH_RIGHT("2", "notEnoughRight");

    ErrorCode(String code, String name) {}
}
