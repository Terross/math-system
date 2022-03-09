package com.mathsystem.domain.user;

import com.mathsystem.authentication.dto.AuthRequest;
import com.mathsystem.authentication.dto.AuthResponse;
import com.mathsystem.authentication.dto.RegistrationRequest;
import com.mathsystem.authentication.jwt.JwtTokenProvider;
import com.mathsystem.domain.user.repository.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity
                .ok(userService.registerUser(registrationRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthRequest request) {
        return ResponseEntity
                .ok(userService.loginUser(request));
    }
}
