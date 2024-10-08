package com.korea.WhereToGo.entity;


import com.korea.WhereToGo.dto.request.notice.PatchNoticeRequestDto;
import com.korea.WhereToGo.dto.request.notice.PostNoticeRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity(name="notice")
@Table(name="notice")
@NoArgsConstructor
@AllArgsConstructor
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    private String title;

    private String content;

    private String nickname;

    private List<String> image ;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime modifyDataTime;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ImageEntity> imageList;

    public NoticeEntity(PostNoticeRequestDto dto){
        this.title=dto.getTitle();
        this.content=dto.getContent();
        this.image=dto.getImageList();
        this.nickname= dto.getNickname();
        this.createDateTime=LocalDateTime.now();
    }
    public void patchNotice(PatchNoticeRequestDto dto){
        this.title=dto.getTitle();
        this.content=dto.getContent();
        this.imageList.clear();
        for (String imageUrl : dto.getImageList()) {
            this.imageList.add(new ImageEntity(imageUrl, this));
        }
        this.modifyDataTime=LocalDateTime.now();
    }
}
