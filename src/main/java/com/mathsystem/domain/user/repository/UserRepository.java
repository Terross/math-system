package com.mathsystem.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
    Optional<User> findUserByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = """
    INSERT INTO system_user(id, first_name, last_name, patronymic, email, password, role, user_group)
    VALUES ('00000000-0000-0000-0000-000000000001',  'Дмитрий', 'Зайков', 'Геннадьевич', 'dima38091@gmail.com', :password, 'ROLE_ADMIN', '8305'),
           ('00000000-0000-0000-0000-000000000002',  'Имя1', 'Фамилия1', null, 'pochta1@mail.ru', :password, 'ROLE_USER', null),
           ('00000000-0000-0000-0000-000000000003',  'Имя2', 'Фамилия2', 'Отчество2', 'pochta2@mail.ru', :password, 'ROLE_USER', '8305'),
           ('00000000-0000-0000-0000-000000000004',  'Имя3', 'Фамилия3', 'Отчество3', 'pochta3@mail.ru', :password, 'ROLE_USER', '8305'),
           ('00000000-0000-0000-0000-000000000005',  'Имя4', 'Фамилия4', null, 'pochta4@mail.ru', :password, 'ROLE_USER', '8305'),
           ('00000000-0000-0000-0000-000000000006',  'Имя5', 'Фамилия5', 'Отчество3', 'pochta5@mail.ru', :password, 'ROLE_USER', null)
    ON CONFLICT
    DO NOTHING
    """, nativeQuery = true)
    void initUsers( @Param("password") String password);
}
