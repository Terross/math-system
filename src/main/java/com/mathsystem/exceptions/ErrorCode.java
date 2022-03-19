package com.mathsystem.exceptions;

public enum ErrorCode {
    USER_ALREADY_EXIST("0", "userAlreadyExist"),
    USER_NOT_FOUND("1", "userNotFound"),
    PLUGIN_ALREADY_EXIST("2", "pluginAlreadyExist"),
    PLUGIN_NOT_FOUND("3", "pluginNotFound"),

    NOT_ENOUGH_RIGHT("4", "notEnoughRight");

    ErrorCode(String code, String name) {}
}
