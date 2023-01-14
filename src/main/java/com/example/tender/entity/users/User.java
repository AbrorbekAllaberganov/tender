package com.example.tender.entity.users;

import com.example.tender.entity.enums.Interest;
import com.example.tender.entity.MyFile;
import com.example.tender.entity.enums.Language;
import com.example.tender.entity.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @OneToOne
    private Parent parent;

    @Column(nullable = false, unique = true)
    String phoneNumber;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    String aboutMe;

    @Column(nullable = false)
    Date birthDay;

    //    true-> male , false -> female
    boolean gender;

    double lon;

    double lat;

    @Column(name = "photo_id")
    private String photoId;

//    @OneToOne(fetch = FetchType.LAZY)
    @OneToOne
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private MyFile photo;

    @Enumerated(EnumType.STRING)
    Language lang;

    @Enumerated(EnumType.STRING)
    UserStatus status;

    LocalDateTime lastOnline;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    List<Interest> interests;

    @CreationTimestamp
    Date createdAt;

    @UpdateTimestamp
    Date updatedAt;
}
