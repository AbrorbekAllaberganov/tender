package com.example.tender.payload.response;

import com.example.tender.mapper.user.UserLikeRatingMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author 'Mukhtarov Sarvarbek' on 22.02.2023
 * @project tender
 * @contact @sarvargo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserShortInfoResDTO {

    String id;

    String name;

    @JsonProperty("photo_url")
    String photoUrl;

    Integer age;

    @JsonProperty("like_count")
    Long likeCount;

    PlacesResponse place;

    public static UserShortInfoResDTO toDTO(UserLikeRatingMapper mapper) {
        return new UserShortInfoResDTO(
                mapper.getId(),
                mapper.getName(),
                "",
                mapper.getAge(),
                mapper.getLikeCount(),
                new PlacesResponse(mapper.getPlaceId(), mapper.getPlaceName()));
    }
}
