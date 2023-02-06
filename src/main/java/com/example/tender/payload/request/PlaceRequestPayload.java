package com.example.tender.payload.request;

import com.example.tender.entity.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 'Mukhtarov Sarvarbek' on 06.02.2023
 * @project tender
 * @contact @sarvargo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceRequestPayload {

    private String nameUz;
    private String nameEn;
    private String nameRu;

    private UserType type;
}
