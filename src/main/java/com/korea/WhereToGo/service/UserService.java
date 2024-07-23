package com.korea.WhereToGo.service;

import com.korea.WhereToGo.dto.request.user.BlockUserRequestDto;
import com.korea.WhereToGo.dto.request.user.ChangePasswordRequestDto;
import com.korea.WhereToGo.dto.request.user.PatchNicknameRequestDto;
import com.korea.WhereToGo.dto.request.user.WithdrawalUserRequestDto;
import com.korea.WhereToGo.dto.response.user.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<? super GetUserResponseDto> getUser(String userId);
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String userId);
    ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto, String userId);
    ResponseEntity<? super ChangePasswordResponseDto> changePassword(ChangePasswordRequestDto dto, String userId);
    ResponseEntity<? super WithdrawalUserResponseDto> withdrawalUser(WithdrawalUserRequestDto dto);
    ResponseEntity<? super PasswordRecoveryResponseDto> passwordRecovery(String email);
    ResponseEntity<? super FindUserIdResponseDto> findUserId(String email);
    ResponseEntity<? super GetUserListResponseDto> getUserList(String userId);
    ResponseEntity<? super DeleteUserResponseDto> deleteUser(String userId);
    ResponseEntity<? super PostReportUserResponseDto> reportUser(String userId);
    ResponseEntity<? super BlockUserResponseDto> blockUser(BlockUserRequestDto dto);
    ResponseEntity<? super GetTop5TemperatureUserResponseDto> getTop5User();
}
