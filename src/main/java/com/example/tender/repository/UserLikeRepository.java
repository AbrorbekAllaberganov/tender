package com.example.tender.repository;

import com.example.tender.entity.UserLike;
import com.example.tender.mapper.user.UserLikeRatingMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserLikeRepository extends JpaRepository<UserLike, String> {

    @Query(nativeQuery = true, value = "select * from user_like where to_user_id= :userId and type= 'LIKE' ")
    List<UserLike> getUserLikes(@Param("userId") String userId);

    @Query(
            nativeQuery = true,
            value = "select u.id id, u.first_name name," +
                    " extract('years' from age(u.birth_day)) age," +
                    " u.photo_id photoId," +
                    " u.type type, u.place_id  placeId," +
                    " (select count(*) likeCount from user_like ul " +
                    " where ul.type in ('LIKE', 'SUPER_LIKE') " +
                    " and ul.to_user_id = u.id) likeCount," +
                    " place.name_uz placeName " +
                    " from users u " +
                    " inner join places place on place.id = u.place_id " +
                    " where u.gender = :gender " +
                    " and u.id in (select t.userId from (select ul.to_user_id userId," +
                    " count(*) likeCount from user_like ul " +
                    " where ul.type in ('LIKE', 'SUPER_LIKE') " +
                    " group by ul.to_user_id " +
                    " order by likeCount desc " +
                    " limit 10) t)"
    )
    List<UserLikeRatingMapper> findRatingByLike(@Param("gender") boolean gender);
}
