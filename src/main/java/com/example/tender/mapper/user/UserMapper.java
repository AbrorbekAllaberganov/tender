package com.example.tender.mapper.user;

import com.example.tender.entity.users.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author 'Mukhtarov Sarvarbek' on 14.01.2023
 * @project tender
 * @contact @sarvargo
 */
@Getter
@Setter
@ToString
public class UserMapper {

    private List<User> list;
    private Long count;

    public UserMapper(List<User> list, Long count) {
        this.list = list;
        this.count = count;
    }

}
