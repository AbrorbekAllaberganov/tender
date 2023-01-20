package com.example.tender.entity;

import com.example.tender.entity.users.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author 'Mukhtarov Sarvarbek' on 19.01.2023
 * @project tender
 * @contact @sarvargo
 */
@Entity
@Table(name = "user_post")
@Getter
@Setter
public class UserPostEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "photo_id")
    private String photoId;

    @OneToOne
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private MyFile photo;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}
