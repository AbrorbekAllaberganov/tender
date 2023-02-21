package com.example.tender.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author 'Mukhtarov Sarvarbek' on 19.02.2023
 * @project tender
 * @contact @sarvargo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopPlacesResponse {

    PlacesResponse university;

    @JsonProperty("work_place")
    PlacesResponse workPlace;
}
