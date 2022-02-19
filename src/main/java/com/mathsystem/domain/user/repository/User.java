package com.mathsystem.domain.user.repository;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "system_user")
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String userName;

    private String email;

    private String password;

    private String userGroup;

    private LocalDateTime created;

    private LocalDateTime updated;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}

