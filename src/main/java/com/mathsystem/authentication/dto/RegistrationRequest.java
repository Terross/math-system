package com.mathsystem.authentication.dto;

public record RegistrationRequest(String firstName,
                                  String lastName,
                                  String email,
                                  String password,
                                  String userGroup) {
}
