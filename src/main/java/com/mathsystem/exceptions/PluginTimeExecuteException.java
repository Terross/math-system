package com.mathsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The plugin takes too long to execute")
public class PluginTimeExecuteException extends RuntimeException {
}
