package com.example.tender.entity;

import com.example.tender.entity.enums.IdeaStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 'Mukhtarov Sarvarbek' on 18.02.2023
 * @project tender
 * @contact @sarvargo
 */
@Entity
@Table(name = "ideas")
@Getter
@Setter
public class IdeaEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "title", columnDefinition = "text")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private IdeaStatus status;

    @Column(name = "visible")
    private Boolean visible = true;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
