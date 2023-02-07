package com.example.tender.repository;

import com.example.tender.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface PlaceRepository extends JpaRepository<PlaceEntity, String> {
    @Query("update PlaceEntity set visible = ?2 where id = ?1")
    @Modifying
    @Transactional
    void updateVisible(String id, Boolean visible);
}