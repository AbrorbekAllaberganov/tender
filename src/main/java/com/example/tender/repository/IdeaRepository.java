package com.example.tender.repository;

import com.example.tender.entity.IdeaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface IdeaRepository extends JpaRepository<IdeaEntity, String> {
    @Modifying
    @Transactional
    @Query("update IdeaEntity set visible = ?1 where id = ?2")
    int updateVisible(Boolean visible,String id);

    Page<IdeaEntity> findAllByVisibleIsTrue(Pageable pageable);
}