package com.mathsystem.domain.user;

import com.mathsystem.domain.task.repository.TaskDecisionRepository;
import com.mathsystem.domain.task.repository.TaskDecisionStory;
import com.mathsystem.domain.user.repository.User;
import com.mathsystem.domain.user.repository.UserRepository;
import com.mathsystem.exceptions.DataException;
import com.mathsystem.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.mathsystem.domain.user.repository.Role.ROLE_ADMIN;
import static com.mathsystem.exceptions.ErrorCode.USER_NOT_FOUND;
import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TaskDecisionRepository taskDecisionRepository;

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
                .orElseThrow(() -> new DataException(USER_NOT_FOUND, "%s not found".formatted(email)));
    }

    public User updateUser(User user, String email) {
        User repoUser = userRepository.findByEmail(email);
        repoUser.setFirstName(user.getFirstName());
        repoUser.setLastName(user.getLastName());
        repoUser.setPatronymic(user.getPatronymic());
        repoUser.setUserGroup(user.getUserGroup());
        userRepository.save(repoUser);
        return user;
    }

    public User upUserToAdmin(String email) {
        User repoUser = userRepository.findByEmail(email);
        repoUser.setRole(ROLE_ADMIN);
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

    public List<TaskDecisionStory> getUserTaskHistory(String email) {
        User user = userRepository
                .findUserByEmail(email)
                .orElseThrow(IllegalArgumentException::new);

        return taskDecisionRepository
                .findAllByUser(user)
                .stream()
                .map(taskDecision -> new TaskDecisionStory(
                        taskDecision.getTask().getName(),
                        taskDecision.getIsRight(),
                        taskDecision.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))))
                .toList();
    }
}
