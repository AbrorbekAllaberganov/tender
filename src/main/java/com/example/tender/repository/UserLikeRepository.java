package com.example.tender.repository;

import com.example.tender.entity.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserLikeRepository extends JpaRepository<UserLike, String> {

    @Query(nativeQuery = true,value = "select * from user_like where to_user_id=:userId and type=`LIKE`")
    List<UserLike> getUserLikes(@Param("userId")String userId);
}
