package com.example.tender.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.tender.entity.users.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName (String name);
}
