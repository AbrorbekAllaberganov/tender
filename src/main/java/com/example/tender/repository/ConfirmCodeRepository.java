package com.example.demo.repository;

import com.example.demo.entity.sms.ConfirmCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConfirmCodeRepository extends JpaRepository<ConfirmCode, UUID> {
    ConfirmCode findByPhoneNumber (String phoneNumber);
}
