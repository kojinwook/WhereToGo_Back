package com.korea.WhereToGo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.korea.WhereToGo.dto.UserDto;
import com.korea.WhereToGo.dto.request.meeting.board.reply.PostReplyToReplyRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "meeting_reply_to_board_reply")
@Table(name = "meeting_reply_to_board_reply")
public class MeetingReplyToReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyReplyId;

    private String replyReply;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @Transient
    private UserDto userDto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    @JsonBackReference
    private MeetingBoardReplyEntity parentComment;

    public MeetingReplyToReplyEntity(PostReplyToReplyRequestDto dto, MeetingBoardReplyEntity parentComment) {
        this.replyReply = dto.getReplyReply();
        this.createDate = LocalDateTime.now();
        this.parentComment = parentComment;
    }
}
