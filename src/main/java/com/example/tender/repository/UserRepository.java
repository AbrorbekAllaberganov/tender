package com.example.tender.repository;

import com.example.tender.entity.PlaceEntity;
import com.example.tender.entity.enums.UserStatus;
import com.example.tender.entity.enums.UserType;
import com.example.tender.mapper.user.TopPlaceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.tender.entity.users.User;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Modifying
    @Transactional
    @Query(" update users set status = ?2 where parent.id = ?1 ")
    void updateStatus(String id, UserStatus status);

    @Modifying
    @Transactional
    @Query(" update users set status = ?2,lastOnline = ?3 where parent.id = ?1 ")
    void updateStatus(String id, UserStatus status, LocalDateTime lastOnline);

    Optional<User> findByParent_PhoneNumber(String parent_phoneNumber);

    List<User> findAllByType(UserType type);
    Optional<User> findFirstByType(UserType type);

    @Query(value = "select place_id placeId,count(*) count from users " +
            " where type = ?1 " +
            " group by place_id " +
            " order by count desc " +
            " limit 1",nativeQuery = true)
    Optional<TopPlaceMapper> findTopPlace(String type);
}
