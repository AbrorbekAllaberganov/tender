package com.example.tender.entity.chat;

import com.example.tender.entity.users.User;
import com.example.tender.entity.users.UserStory;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Message {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    String id;

    @OneToOne
    Message replyMessageId;

    String text;

    @OneToOne
    User fromUser;

    @ManyToOne(fetch = FetchType.EAGER)
    UserStory story;

    @CreationTimestamp
    Date createdAt;

}
