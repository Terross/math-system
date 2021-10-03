package com.mathsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class SomethingWrongInPluginException extends RuntimeException {
    private String message;
    public SomethingWrongInPluginException(String str) {
        this.message = str;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
