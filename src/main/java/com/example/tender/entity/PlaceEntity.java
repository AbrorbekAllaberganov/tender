package com.example.tender.entity;

import com.example.tender.entity.enums.UserType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author 'Mukhtarov Sarvarbek' on 06.02.2023
 * @project tender
 * @contact @sarvargo
 */
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "places")
@Getter
@Setter
public class PlaceEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    String id;

    String nameUz;
    String nameRu;
    String nameEn;
    @Enumerated(EnumType.STRING)
    UserType type;
    Boolean visible;
}
