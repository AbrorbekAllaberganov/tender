package com.example.tender.payload.request.user;

import com.example.tender.entity.enums.Language;
import com.example.tender.entity.enums.UserType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPayload {
    @NotNull(message = "Password required!")
    String password;

    @NotNull(message = "PhoneNumber required!")
    String phoneNumber;

    @NotNull(message = "FirstName required!")
    String firstName;

    @NotNull(message = "LastName required!")
    String lastName;

    @NotNull(message = "About required!")
    String aboutMe;
    @NotNull(message = "Firebase token required!")
    String firebaseToken;

    String placeId;
    UserType type;
    String photoId;

    Language language;

    Date birthDay;

    List<String> interests;

    boolean gender;

    double lon;

    double lat;
}
