package com.example.mathsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "PluginAlreadyExists")
public class PluginAlreadyExistsException extends RuntimeException{
}
