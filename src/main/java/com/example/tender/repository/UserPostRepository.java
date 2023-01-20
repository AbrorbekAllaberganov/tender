package com.example.tender.repository;

import com.example.tender.entity.UserPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPostRepository extends JpaRepository<UserPostEntity, String> {
    List<UserPostEntity> findAllByUserId(String userId);
}