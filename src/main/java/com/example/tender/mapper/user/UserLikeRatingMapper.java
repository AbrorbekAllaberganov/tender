package com.example.tender.mapper.user;

import com.example.tender.entity.enums.UserType;

/**
 * @author 'Mukhtarov Sarvarbek' on 23.02.2023
 * @project tender
 * @contact @sarvargo
 */
public interface UserLikeRatingMapper {
    String getId();

    Integer getAge();

    Long getLikeCount();

    String getName();
    String getPlaceName();

    String getPhotoId();

    String getPlaceId();

    UserType getType();
}
