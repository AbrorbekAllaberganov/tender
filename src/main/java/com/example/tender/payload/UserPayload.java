package com.example.tender.payload;

import com.example.tender.entity.Interest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.ElementCollection;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPayload {
    String password;
    String phoneNumber;
    String firstName;
    String lastName;
    String aboutMe;
    Date birthDay;
    List<String> interests;
    boolean gender;
    double lon;
    double lat;

}
