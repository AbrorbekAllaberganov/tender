package com.example.tender.payload.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author 'Mukhtarov Sarvarbek' on 22.02.2023
 * @project tender
 * @contact @sarvargo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LikeRatingResDTO {

    List<UserShortInfoResDTO> boys;

    List<UserShortInfoResDTO> girls;



}
