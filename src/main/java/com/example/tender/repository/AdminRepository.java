package com.example.tender.repository;


import uz.abror.myproject.entity.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {

    @Query(value = "select * from admin a\n" +
            "inner join parent p on a.parent_id=p.id\n" +
            "where p.user_name=:USERNAME",nativeQuery = true)
    Admin getAdminByUsername(@Param("USERNAME")String username);

}
