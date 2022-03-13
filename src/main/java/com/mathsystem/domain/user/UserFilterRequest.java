package com.mathsystem.domain.user;

import java.util.Optional;

public record UserFilterRequest(Optional<String> firstName,
                                Optional<String> lastName,
                                Optional<String> patronymic,
                                Optional<String> email,
                                Optional<String> userGroup) {
}
