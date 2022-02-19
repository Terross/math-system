package com.mathsystem.domain.user;

import com.mathsystem.domain.user.repository.Role;
import com.mathsystem.domain.user.repository.RoleRepository;
import com.mathsystem.domain.user.repository.User;
import com.mathsystem.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByLogin(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByLoginAndPassword(String login, String password) {
        User userEntity = findByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }

    public User findUserById(UUID id) {
        return userRepository
                .findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }
}
