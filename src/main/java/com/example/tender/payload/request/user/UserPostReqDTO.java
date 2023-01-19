package com.example.tender.payload.request.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author 'Mukhtarov Sarvarbek' on 19.01.2023
 * @project tender
 * @contact @sarvargo
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPostReqDTO {

    List<String> photos;
}
