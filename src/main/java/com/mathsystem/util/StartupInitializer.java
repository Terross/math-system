package com.mathsystem.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupInitializer implements ApplicationRunner {
    private final DataLoader dataLoader;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        dataLoader.initRole();
        dataLoader.initUsers();
    }
}
