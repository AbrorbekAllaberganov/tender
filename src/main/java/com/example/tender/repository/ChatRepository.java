package com.example.tender.repository;

import com.example.tender.entity.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {
    @Query(value = "select * from chat where user1_id=:userId or user2_id=:userId", nativeQuery = true)
    List<Chat> getChatsByUserId(@Param("userId") String userId);

}
