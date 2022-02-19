package com.mathsystem.domain.user.repository;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
}
