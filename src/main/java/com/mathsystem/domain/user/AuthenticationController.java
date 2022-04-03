package com.mathsystem.domain.user;

import com.mathsystem.authentication.dto.AuthRequest;
import com.mathsystem.authentication.dto.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity
                .ok(authService.registerUser(registrationRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthRequest request) {
        return ResponseEntity
                .ok(authService.loginUser(request));
    }
}
