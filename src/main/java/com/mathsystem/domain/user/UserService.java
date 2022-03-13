package com.mathsystem.domain.user;

import com.mathsystem.domain.user.repository.RoleRepository;
import com.mathsystem.domain.user.repository.User;
import com.mathsystem.domain.user.repository.UserRepository;
import com.mathsystem.exceptions.ErrorCode;
import com.mathsystem.exceptions.SqlNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getFilteredUsers(UserFilterRequest userFilterRequest) {
        return userRepository
                .findAll()
                .stream()
                .filter(user-> filterUser(user, userFilterRequest))
                .toList();
    }

    public User getUserByEmail(String email) {
        return userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new SqlNotFoundException("%s not found".formatted(email), ErrorCode.USER_NOT_FOUND));
    }

    public User updateUser(User user, String email) {
        User repoUser = userRepository.findByEmail(email);
        repoUser.setUpdated(LocalDateTime.now());
        repoUser.setFirstName(user.getFirstName());
        repoUser.setLastName(user.getLastName());
        repoUser.setPatronymic(user.getPatronymic());
        repoUser.setUserGroup(user.getUserGroup());
        userRepository.save(repoUser);
        return user;
    }

    public User upUserToAdmin(String email) {
        User repoUser = userRepository.findByEmail(email);
        repoUser.setRole(roleRepository.findByName("ROLE_ADMIN"));
        repoUser.setUpdated(LocalDateTime.now());
        return userRepository.save(repoUser);
    }

    public String deleteUser(String email) {
        userRepository.delete(userRepository.findByEmail(email));
        return email;
    }

    private boolean filterUser(User user, UserFilterRequest userFilterRequest) {
        return user.getFirstName().equals(userFilterRequest.firstName().orElse(user.getFirstName())) &&
                user.getLastName().equals(userFilterRequest.lastName().orElse(user.getLastName())) &&
                user.getPatronymic().equals(userFilterRequest.patronymic().orElse(user.getPatronymic())) &&
                user.getEmail().equals(userFilterRequest.email().orElse(user.getEmail())) &&
                user.getUserGroup().equals(userFilterRequest.userGroup().orElse(user.getUserGroup()));
    }
}
