package com.example.tender.repository;

import com.example.tender.entity.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLikeRepository extends JpaRepository<UserLike, String> {
    List<UserLike> findAllByToUser_IdOrderByCreatedAt(String userId);
}
