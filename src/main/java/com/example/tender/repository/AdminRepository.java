package com.example.tender.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.tender.entity.users.Admin;

import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    @Query(value = "select * from admin a " +
            "inner join parent p on a.parent_id=p.id " +
            "where p.user_name= :USERNAME ",nativeQuery = true)
    Admin getAdminByUsername(@Param("USERNAME")String username);

}
