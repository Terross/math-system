package com.mathsystem.authentication.dto;

public record RegistrationRequest(String userName,
                                  String email,
                                  String password,
                                  String userGroup) {
}
