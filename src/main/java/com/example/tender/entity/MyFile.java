package com.example.tender.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class MyFile implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String link;

//    @JsonIgnore
    private String name;

    private String hashId;

//    @JsonIgnore
    private String uploadPath;
//    @JsonIgnore
    private String contentType;
//    @JsonIgnore
    private String extension;
//    @JsonIgnore
    private Long fileSize;

//    @JsonIgnore
    @CreationTimestamp
    Date createdAt;

//    @JsonIgnore
    @UpdateTimestamp
    Date updatedAt;

}
