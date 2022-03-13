package com.mathsystem.domain.user;

import com.mathsystem.domain.user.repository.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/all/user/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable(name = "email") String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping("/all/filterUsers")
    public ResponseEntity<?> getFilteredUsers(@RequestBody UserFilterRequest userFilterRequest) {
        return ResponseEntity.ok(userService.getFilteredUsers(userFilterRequest));
    }

    @GetMapping("/all/allUsers")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/all/user/{email}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable(name = "email") String email) {
        return ResponseEntity.ok(userService.updateUser(user, email));
    }

    @PutMapping("/admin/user/up/{email}")
    public ResponseEntity<?> upUserToAdmin(@PathVariable(name = "email") String email) {
        return ResponseEntity.ok(userService.upUserToAdmin(email));
    }

    @DeleteMapping("/all/user/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "email") String email) {
        return ResponseEntity.ok(userService.deleteUser(email));
    }
}
