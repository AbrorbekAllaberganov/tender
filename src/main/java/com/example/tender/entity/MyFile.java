package uz.abror.myproject.entity;

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
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

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
