package com.example.tender.json.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataDTO {
    String send;
    String text;
    String number;
    String user_id;
    String token;
    long id;

    @Override
    public String toString() {
        return "{" +
                "\"send\":\"" + send + '\"' +
                ", \"text\":\"" + text + '\"' +
                ", \"number\":\"" + number + '\"' +
                ", \"user_id\":\"" + user_id + '\"' +
                ", \"token\":\"" + token + '\"' +
                ", \"id\":" + id +
                '}';
    }
}
