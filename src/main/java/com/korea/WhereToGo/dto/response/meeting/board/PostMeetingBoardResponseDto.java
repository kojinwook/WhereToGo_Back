package com.korea.WhereToGo.dto.response.meeting.board;

import com.korea.WhereToGo.common.ResponseCode;
import com.korea.WhereToGo.common.ResponseMessage;
import com.korea.WhereToGo.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PostMeetingBoardResponseDto extends ResponseDto {

    public PostMeetingBoardResponseDto(){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<? super PostMeetingBoardResponseDto> success(){
        PostMeetingBoardResponseDto responseBody = new PostMeetingBoardResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> notExistMeeting(){
        ResponseDto responseBody = new ResponseDto(ResponseCode.NOT_EXISTED_MEETING, ResponseMessage.NOT_EXISTED_MEETING);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> notExistUser(){
        ResponseDto responseBody = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }
}
