package com.mathsystem.domain.user.repository;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
public class Role {
    @Id
    private String name;
}
