package com.example.tender.entity;

import com.example.tender.entity.enums.LikeType;
import com.example.tender.entity.users.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserLike {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @OneToOne
    User toUser;

    @ManyToOne
    User fromUser;

    @Enumerated(EnumType.STRING)
    LikeType type;

    @CreationTimestamp
    Date createdAt;

    @CreationTimestamp
    Date updatedAt;
}
