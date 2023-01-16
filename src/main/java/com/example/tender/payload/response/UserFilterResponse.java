package com.example.tender.payload.response;

import com.example.tender.payload.detail.FileForResponse;
import lombok.Data;

/**
 * @author 'Mukhtarov Sarvarbek' on 14.01.2023
 * @project tender
 * @contact @sarvargo
 */
@Data
public class UserFilterResponse {

    private String id;

    private String lastName;

    private String firstName;

    private Integer age;

    private FileForResponse photo;
}
