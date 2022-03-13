package com.mathsystem.domain.user;

import com.mathsystem.authentication.dto.AuthRequest;
import com.mathsystem.authentication.dto.AuthResponse;
import com.mathsystem.authentication.dto.RegistrationRequest;
import com.mathsystem.authentication.jwt.JwtTokenProvider;
import com.mathsystem.domain.user.repository.Role;
import com.mathsystem.domain.user.repository.RoleRepository;
import com.mathsystem.domain.user.repository.User;
import com.mathsystem.domain.user.repository.UserRepository;
import com.mathsystem.exceptions.SqlConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.mathsystem.exceptions.ErrorCode.USER_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtProvider;

    public AuthResponse registerUser(RegistrationRequest request) {
        User user = new User();
        user.setPassword(request.password());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPatronymic(request.patronymic());
        user.setEmail(request.email());
        user.setUserGroup(request.userGroup());
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        this.saveUser(user);
        String token = jwtProvider.generateToken(user.getEmail());
        return new AuthResponse(user, token);
    }

    public AuthResponse loginUser(AuthRequest request) {
        User user = this.findByLoginAndPassword(request.email(), request.password());
        String token = jwtProvider.generateToken(user.getEmail());
        return new AuthResponse(user, token);
    }

    public User saveUser(User user) {
        try {
            Role userRole = roleRepository.findByName("ROLE_USER");
            user.setRole(userRole);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (Exception e) {
            throw new SqlConflictException(e.getMessage(), USER_ALREADY_EXIST);
        }
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
