package com.mathsystem.domain.user.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
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

//    @Column(unique = true)
    private String email;

    private String password;

    private String userGroup;

    private LocalDateTime created;

    private LocalDateTime updated;

    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;
}

