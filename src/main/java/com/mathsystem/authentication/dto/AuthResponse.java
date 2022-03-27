package com.mathsystem.authentication.dto;

import com.mathsystem.domain.user.repository.User;

public record AuthResponse (User user, String token) {
}
