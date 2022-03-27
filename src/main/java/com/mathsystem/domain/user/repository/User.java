package com.mathsystem.domain.user.repository;

import com.mathsystem.domain.task.repository.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "system_user")
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;

    private String lastName;

    private String patronymic;

    private String email;

    private String password;

    private String userGroup;

    @Enumerated(EnumType.STRING)
    private Role role;
}

