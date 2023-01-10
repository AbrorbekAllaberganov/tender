package com.example.tender.repository;

import com.example.tender.entity.MyFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface MyFileRepository extends JpaRepository<MyFile, UUID> {
    boolean deleteByHashId(String hashId);

    Optional<MyFile> findByHashId(String hashId);

    @Query(value ="select f.hash_id from my_file" ,nativeQuery = true)
    Page<String> getHashId(Pageable pageable);
}
