package com.example.tender.repository;

import com.example.tender.entity.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.tender.entity.users.User;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Modifying
    @Transactional
    @Query(" update users set status = ?2 where parent.id = ?1 ")
    void updateStatus(String id, UserStatus status);

    @Modifying
    @Transactional
    @Query(" update users set status = ?2,lastOnline = ?3 where parent.id = ?1 ")
    void updateStatus(String id, UserStatus status, LocalDateTime lastOnline);

    Optional<User> findByParent_PhoneNumber(String parent_phoneNumber);
}
