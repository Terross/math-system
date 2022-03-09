package com.mathsystem.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    @Transactional
    @Modifying
    @Query(value = """
        INSERT INTO role(id, name)
        VALUES ('796fa019-7b59-4e7b-b5b2-24e395a4aceb', 'ROLE_USER'),
               ('796fa019-7b59-4e7b-b5b2-24e395a4acec', 'ROLE_ADMIN')
        ON CONFLICT
        DO NOTHING
    """, nativeQuery = true)
    void initRole();

    Role findByName(String role_user);
}
