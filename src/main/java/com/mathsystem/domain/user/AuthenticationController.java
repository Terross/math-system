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

@RestController
@RequestMapping(value = "/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final JwtTokenProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        User user = new User();
        user.setPassword(registrationRequest.password());
        user.setUserName(registrationRequest.userName());
        user.setEmail(registrationRequest.email());
        user.setUserGroup(registrationRequest.userGroup());
        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthRequest request) {
        User user = userService.findByLoginAndPassword(request.email(), request.password());
        String token = jwtProvider.generateToken(user.getUserName());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
