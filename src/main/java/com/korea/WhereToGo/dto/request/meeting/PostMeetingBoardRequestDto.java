package com.korea.WhereToGo.dto.request.meeting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostMeetingBoardRequestDto {
    private String title;
    private String content;
    private String address;
    private List<String> imageList;
}