package com.example.tender.entity.users;

import com.example.tender.entity.MyFile;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author 'Mukhtarov Sarvarbek' on 28.01.2023
 * @project tender
 * @contact @sarvargo
 */
@Entity
@Table(name = "user_story")
@Getter
@Setter
public class UserStory {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;


    @Column(name = "media_id")
    private String mediaId;

    @OneToOne
    @JoinColumn(name = "media_id", insertable = false, updatable = false)
    private MyFile media;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    private Boolean isActive = true;
    @CreationTimestamp
    private LocalDateTime createdAt;


}
