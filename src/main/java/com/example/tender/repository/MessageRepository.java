package com.example.tender.repository;

import com.example.tender.entity.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface MessageRepository extends JpaRepository<Message,String> {

    @Modifying
    @Transactional
    @Query(value = "update message set story_id =NULL  where story_id=:ID",nativeQuery = true)
    void updateMessageStoryToNull(@Param("ID")String id);
}
