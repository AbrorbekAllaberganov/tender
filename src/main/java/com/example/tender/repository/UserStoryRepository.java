package com.example.tender.repository;

import com.example.tender.entity.users.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface UserStoryRepository extends JpaRepository<UserStory, String> {

    @Transactional
    @Modifying
    void deleteAllByCreatedAtBefore(LocalDateTime date);

    List<UserStory> findAllByCreatedAtBefore(LocalDateTime date);

}