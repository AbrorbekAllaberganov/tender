package com.example.tender.repository;

import com.example.tender.entity.users.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStoryRepository extends JpaRepository<UserStory, String> {

}