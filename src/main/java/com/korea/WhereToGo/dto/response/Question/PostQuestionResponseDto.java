package com.korea.WhereToGo.dto.response.Question;

import com.korea.WhereToGo.common.ResponseCode;
import com.korea.WhereToGo.common.ResponseMessage;
import com.korea.WhereToGo.dto.response.ResponseDto;
import lombok.Getter;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PostQuestionResponseDto extends ResponseDto {

    private PostQuestionResponseDto(){super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);}

    public static ResponseEntity<PostQuestionResponseDto> success(){
        PostQuestionResponseDto responseBody = new PostQuestionResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }



}
