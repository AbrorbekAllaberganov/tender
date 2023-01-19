package com.example.tender.repository;

import com.example.tender.entity.UserPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPostRepository extends JpaRepository<UserPostEntity, String> {
}