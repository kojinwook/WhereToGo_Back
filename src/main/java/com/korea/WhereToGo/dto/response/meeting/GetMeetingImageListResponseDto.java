package com.korea.WhereToGo.dto.response.meeting;

import com.korea.WhereToGo.common.ResponseCode;
import com.korea.WhereToGo.common.ResponseMessage;
import com.korea.WhereToGo.dto.response.ResponseDto;
import com.korea.WhereToGo.entity.ImageEntity;
import com.korea.WhereToGo.entity.MeetingEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetMeetingImageListResponseDto extends ResponseDto {
    private List<ImageEntity> imageList;

    private GetMeetingImageListResponseDto(List<ImageEntity> imageList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.imageList = imageList;
    }

    public  static ResponseEntity<GetMeetingImageListResponseDto> success(List<ImageEntity> imageList){
        GetMeetingImageListResponseDto responseDto = new GetMeetingImageListResponseDto(imageList);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<ResponseDto> notExistMeeting() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NOT_EXISTED_MEETING, ResponseMessage.NOT_EXISTED_MEETING);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}