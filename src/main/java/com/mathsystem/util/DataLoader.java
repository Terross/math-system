package com.mathsystem.util;

import com.mathsystem.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void initUsers() {
        userRepository.initUsers(passwordEncoder.encode("pass"));
    }
}
