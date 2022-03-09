package com.mathsystem.util;

import com.mathsystem.domain.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final RoleRepository roleRepository;

    public void run(ApplicationArguments args) {
        roleRepository.initRole();
    }
}
