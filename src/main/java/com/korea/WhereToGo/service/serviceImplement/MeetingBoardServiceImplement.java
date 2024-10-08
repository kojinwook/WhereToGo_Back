package com.korea.WhereToGo.service.serviceImplement;

import com.korea.WhereToGo.dto.ImageWithBoardIdDto;
import com.korea.WhereToGo.dto.UserDto;
import com.korea.WhereToGo.dto.request.meeting.board.PatchMeetingBoardRequestDto;
import com.korea.WhereToGo.dto.request.meeting.board.PostMeetingBoardRequestDto;
import com.korea.WhereToGo.dto.response.ResponseDto;
import com.korea.WhereToGo.dto.response.meeting.GetMeetingImageListResponseDto;
import com.korea.WhereToGo.dto.response.meeting.board.*;
import com.korea.WhereToGo.entity.ImageEntity;
import com.korea.WhereToGo.entity.MeetingBoardEntity;
import com.korea.WhereToGo.entity.MeetingEntity;
import com.korea.WhereToGo.entity.UserEntity;
import com.korea.WhereToGo.repository.*;
import com.korea.WhereToGo.service.MeetingBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingBoardServiceImplement implements MeetingBoardService {

    private final MeetingRepository meetingRepository;
    private final MeetingBoardRepository meetingBoardRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Override
    public ResponseEntity<? super PostMeetingBoardResponseDto> postMeetingBoard(PostMeetingBoardRequestDto dto, Long meetingId, String userId) {
        try {
            MeetingEntity meetingEntity = meetingRepository.findByMeetingId(meetingId);
            if (meetingEntity == null) return PostMeetingBoardResponseDto.notExistMeeting();

            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return PostMeetingBoardResponseDto.notExistUser();
            userEntity.increaseTemperature(0.5);
            userRepository.save(userEntity);

            MeetingBoardEntity meetingBoardEntity = new MeetingBoardEntity(dto);
            meetingBoardEntity.setMeeting(meetingEntity);
            meetingBoardEntity.setUser(userEntity);

            meetingBoardRepository.save(meetingBoardEntity);

            List<String> imageList = dto.getImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            for (String image : imageList) {
                ImageEntity imageEntity = new ImageEntity(image, meetingBoardEntity, userId);
                imageEntity.setMeetingBoard(meetingBoardEntity);
                imageEntities.add(imageEntity);
            }
            imageRepository.saveAll(imageEntities);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostMeetingBoardResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PatchMeetingBoardResponseDto> patchMeetingBoard(PatchMeetingBoardRequestDto dto, Long boardId, String userId) {
        try {
            MeetingBoardEntity meetingBoardEntity = meetingBoardRepository.findByMeetingBoardId(boardId);
            if (meetingBoardEntity == null) return PatchMeetingBoardResponseDto.notExistMeetingBoard();

            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return PatchMeetingBoardResponseDto.notExistUser();

            if (!meetingBoardEntity.getUser().getUserId().equals(userId))
                return PatchMeetingBoardResponseDto.noPermission();

            meetingBoardEntity.patchMeetingBoard(dto, userId);

            meetingBoardRepository.save(meetingBoardEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchMeetingBoardResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetMeetingBoardListResponseDto> getMeetingBoardList(Long meetingId) {
        List<MeetingBoardEntity> meetingBoardEntityList = new ArrayList<>();
        try {
            List<MeetingBoardEntity> meetingBoardList = meetingBoardRepository.findByMeeting_MeetingId(meetingId);

            for (MeetingBoardEntity meetingBoardEntity : meetingBoardList) {
                UserEntity userEntity = meetingBoardEntity.getUser();
                if (userEntity != null) {
                    UserDto userDto = new UserDto(userEntity);
                    meetingBoardEntity.setUserDto(userDto);
                }
                meetingBoardEntityList.add(meetingBoardEntity);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetMeetingBoardListResponseDto.success(meetingBoardEntityList);
    }

    @Override
    public ResponseEntity<? super GetMeetingBoardResponseDto> getMeetingBoard(Long boardId) {
        MeetingBoardEntity meetingBoardEntity = new MeetingBoardEntity();
        try {
            meetingBoardEntity = meetingBoardRepository.findByMeetingBoardId(boardId);
            if (meetingBoardEntity == null) return GetMeetingBoardResponseDto.notExistMeetingBoard();

            UserEntity userEntity = meetingBoardEntity.getUser();
            if (userEntity != null) {
                UserDto userDto = new UserDto(userEntity);
                meetingBoardEntity.setUserDto(userDto);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetMeetingBoardResponseDto.success(meetingBoardEntity);
    }

    @Override
    public ResponseEntity<? super GetUserBoardResponseDto> getUserBoard(String userId) {
        List<MeetingBoardEntity> boardList = new ArrayList<>();
        try {
            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return GetUserBoardResponseDto.notExistedUser();

            boardList = meetingBoardRepository.findByUser_UserId(userId);

            if (boardList == null) return GetUserBoardResponseDto.notExistedBoard();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetUserBoardResponseDto.success(boardList);
    }

    @Override
    public ResponseEntity<? super DeleteMeetingBoardResponseDto> deleteMeetingBoard(Long boardId, String userId) {
        try {
            MeetingBoardEntity meetingBoardEntity = meetingBoardRepository.findByMeetingBoardId(boardId);
            if (meetingBoardEntity == null) return DeleteMeetingBoardResponseDto.notExistMeetingBoard();

            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return DeleteMeetingBoardResponseDto.notExistUser();

            if (!meetingBoardEntity.getUser().getUserId().equals(userId))
                return DeleteMeetingBoardResponseDto.noPermission();

            meetingBoardRepository.delete(meetingBoardEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return DeleteMeetingBoardResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetMeetingImageListResponseDto> getMeetingImageList(Long meetingId) {
        List<ImageEntity> imageEntityList = new ArrayList<>();
        List<ImageWithBoardIdDto> imageDtos = new ArrayList<>();
        try {
            List<ImageEntity> meetingImages = imageRepository.findByMeeting_MeetingId(meetingId);
            imageEntityList.addAll(meetingImages);

            List<ImageEntity> meetingBoardImages = imageRepository.findByMeetingBoard_Meeting_MeetingId(meetingId);
            imageEntityList.addAll(meetingBoardImages);

            MeetingEntity meetingEntity = meetingRepository.findByMeetingId(meetingId);
            if (meetingEntity == null) {
                return GetMeetingImageListResponseDto.notExistMeeting();
            }

            imageDtos = imageEntityList.stream()
                    .map(image -> new ImageWithBoardIdDto(
                            image.getId(),
                            image.getImage(),
                            image.getMeetingBoard() != null ? image.getMeetingBoard().getMeetingBoardId() : null))
                    .collect(Collectors.toList());

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetMeetingImageListResponseDto.success(imageDtos);
    }

    @Override
    public ResponseEntity<? super GetMeetingBoardsTitleResponseDto> getMeetingBoardsTitle(List<Long> meetingBoardIds) {
        List<String> meetingBoardTitles = new ArrayList<>();
        try {
            if (meetingBoardIds == null || meetingBoardIds.isEmpty()) {
                return GetMeetingBoardsTitleResponseDto.success(meetingBoardTitles);
            }

            List<MeetingBoardEntity> meetingBoardList = meetingBoardRepository.findAllById(meetingBoardIds);

            for (MeetingBoardEntity meetingBoardEntity : meetingBoardList) {
                meetingBoardTitles.add(meetingBoardEntity.getTitle());
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetMeetingBoardsTitleResponseDto.success(meetingBoardTitles);
    }
}
