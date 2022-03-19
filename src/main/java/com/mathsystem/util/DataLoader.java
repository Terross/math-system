package com.mathsystem.util;

import com.mathsystem.domain.user.repository.RoleRepository;
import com.mathsystem.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void initRole() {
        roleRepository.initRole();
    }

    public void initUsers() {
        userRepository.initUsers(LocalDateTime.now(), passwordEncoder.encode("pass"));
    }
}
