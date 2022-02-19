package com.mathsystem.authentication.dto;


public record AuthRequest(String email,
                          String password) {
}
