package com.mathsystem.domain.user;

import com.mathsystem.domain.user.repository.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/admin")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }
}
