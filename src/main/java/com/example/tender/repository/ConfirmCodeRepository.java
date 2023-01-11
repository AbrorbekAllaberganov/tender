package com.example.tender.repository;

import com.example.tender.entity.ConfirmCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConfirmCodeRepository extends JpaRepository<ConfirmCode, String> {
    ConfirmCode findByPhoneNumber (String phoneNumber);
}
