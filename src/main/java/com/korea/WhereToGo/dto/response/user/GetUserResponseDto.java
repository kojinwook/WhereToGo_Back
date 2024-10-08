package com.korea.WhereToGo.dto.response.user;

import com.korea.WhereToGo.common.ResponseCode;
import com.korea.WhereToGo.common.ResponseMessage;
import com.korea.WhereToGo.dto.response.ResponseDto;
import com.korea.WhereToGo.entity.UserEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetUserResponseDto extends ResponseDto {

    private String userId;
    private String email;
    private String password;
    private String nickname;
    private String profileImage;
    private String phoneNumber;
    private String role;
    private double temperature;

    private GetUserResponseDto(UserEntity userEntity){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = userEntity.getUserId();
        this.email = userEntity.getEmail();
        this.nickname = userEntity.getNickname();
        this.password = userEntity.getPassword();
        this.profileImage = userEntity.getProfileImage();
        this.phoneNumber = userEntity.getPhoneNumber();
        this.role = userEntity.getRole();
        this.temperature = userEntity.getTemperature();
    }

    public static ResponseEntity<GetUserResponseDto> success(UserEntity userEntity){
        GetUserResponseDto responseDto = new GetUserResponseDto(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<ResponseDto> notExistUser(){
        ResponseDto responseBody = new ResponseDto(ResponseCode.NOT_EXISTED_USER,ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(responseBody);
    }
}
