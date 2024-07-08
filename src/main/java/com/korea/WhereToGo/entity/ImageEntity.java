package com.korea.WhereToGo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "image")
@Table(name = "image")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userId;
    private String contentId;
    private String image;

    public ImageEntity(String contentId, String image, String userId){
        this.userId = userId;
        this.contentId = contentId;
        this.image = image;
    }
}
