package com.mathsystem.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface RoleRepository extends JpaRepository<Role, String> {

    @Transactional
    @Modifying
    @Query(value = """
        INSERT INTO role(name)
        VALUES ('ROLE_USER'),
               ('ROLE_ADMIN')
        ON CONFLICT
        DO NOTHING
    """, nativeQuery = true)
    void initRole();

    Role findByName(String role_user);
}
