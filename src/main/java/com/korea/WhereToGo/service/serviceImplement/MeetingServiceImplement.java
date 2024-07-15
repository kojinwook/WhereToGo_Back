package com.korea.WhereToGo.service.serviceImplement;

import com.korea.WhereToGo.dto.request.meeting.PostMeetingRequestDto;
import com.korea.WhereToGo.dto.response.ResponseDto;
import com.korea.WhereToGo.dto.response.meeting.GetAllMeetingResponseDto;
import com.korea.WhereToGo.dto.response.meeting.GetMeetingResponseDto;
import com.korea.WhereToGo.dto.response.meeting.PostMeetingResponseDto;
import com.korea.WhereToGo.entity.ImageEntity;
import com.korea.WhereToGo.entity.MeetingEntity;
import com.korea.WhereToGo.repository.ImageRepository;
import com.korea.WhereToGo.repository.MeetingRepository;
import com.korea.WhereToGo.repository.UserRepository;
import com.korea.WhereToGo.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingServiceImplement implements MeetingService {
    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;
    private final ImageRepository imageRepository;

    @Override
    public ResponseEntity<? super GetMeetingResponseDto> getMeeting(Long meetingId) {
        MeetingEntity meetingEntity = null;
        try {
            meetingEntity = meetingRepository.findByMeetingId(meetingId);
            if (meetingEntity == null) return GetMeetingResponseDto.notExistMeeting();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetMeetingResponseDto.success(meetingEntity);
    }

    @Override
    public ResponseEntity<? super PostMeetingResponseDto> postMeeting(PostMeetingRequestDto dto) {
        String nickname = dto.getNickname();
        try {
//            boolean userEntity = userRepository.existsByNickname(nickname);
//            if(!userEntity) return PostMeetingResponseDto.notExistUser();

            MeetingEntity meetingEntity = new MeetingEntity(dto);
            meetingRepository.save(meetingEntity);

            String meetingImageUrl = dto.getImageUrl();
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setImage(meetingImageUrl);
            imageEntity.setMeeting(meetingEntity);
            imageRepository.save(imageEntity);

            meetingEntity.setImageList(imageEntity);
            meetingRepository.save(meetingEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostMeetingResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetAllMeetingResponseDto> getAllMeeting() {
        List<MeetingEntity> meetingList = new ArrayList<>();
        try {
            meetingList = meetingRepository.findAll();
            if (meetingList.isEmpty()) return GetAllMeetingResponseDto.notExistMeeting();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetAllMeetingResponseDto.success(meetingList);
    }
}