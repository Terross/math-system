package com.mathsystem.authentication.dto;

public record RegistrationRequest(String firstName,
                                  String lastName,
                                  String patronymic,
                                  String email,
                                  String password,
                                  String userGroup) {
}
