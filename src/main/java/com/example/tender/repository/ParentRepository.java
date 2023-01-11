package com.example.tender.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.tender.entity.users.Parent;

import java.util.UUID;

@Repository
public interface ParentRepository extends JpaRepository<Parent, String> {
    Parent findByPhoneNumber (String phoneNumber);
}
