package com.korea.WhereToGo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korea.WhereToGo.dto.request.chat.PostChatMessageRequestDto;
import com.korea.WhereToGo.dto.request.meeting.board.reply.PostBoardReplyRequestDto;
import com.korea.WhereToGo.dto.request.notification.ChatNotificationRequestDto;
import com.korea.WhereToGo.dto.request.notification.ReplyNotificationRequestDto;
import com.korea.WhereToGo.dto.response.chat.GetChatMessageListResponseDto;
import com.korea.WhereToGo.dto.response.chat.GetChatMessageResponseDto;
import com.korea.WhereToGo.dto.response.chat.GetSavedMessageResponseDto;
import com.korea.WhereToGo.entity.*;
import com.korea.WhereToGo.repository.ChatMessageRepository;
import com.korea.WhereToGo.repository.ChatRoomRepository;
import com.korea.WhereToGo.repository.MeetingBoardRepository;
import com.korea.WhereToGo.repository.UserRepository;
import com.korea.WhereToGo.service.ChatService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MeetingBoardRepository meetingBoardRepository;
    private final UserRepository userRepository;
    private final ChatService chatService;

    @MessageMapping("/chat/room.{roomId}")
    public void getChatMessageList(@Payload Long roomId, Principal principal) {
        System.out.println("Fetching messages for room: " + roomId);
        ResponseEntity<? super GetChatMessageListResponseDto> response = chatService.getChatMessageList(roomId);

        if (response.getBody() == null) {
            System.out.println("Failed to retrieve chat message list");
            return;
        }
        messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/chat." + roomId, response.getBody());
        System.out.println("Messages sent to /queue/chat." + roomId + " for user: " + principal.getName());
    }

    @MessageMapping("/chat/status")
    public void afterConnectionEstablished() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        messagingTemplate.convertAndSend("/topic/status", new UserStatus(username, true));
    }

    @MessageMapping("/chat/disconnect")
    public void afterConnectionClosed() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        messagingTemplate.convertAndSend("/topic/status", new UserStatus(username, false));
    }

    @MessageMapping("/board/reply")
    public void notifyPostReply(@Payload PostBoardReplyRequestDto dto,
                                @Payload String jsonAuthentication) throws JsonProcessingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AuthenticationMessage authenticationMessage = objectMapper.readValue(jsonAuthentication, AuthenticationMessage.class);

            String username = authenticationMessage.getReplySender();

            MeetingBoardEntity meetingBoard = meetingBoardRepository.findByMeetingBoardId(dto.getMeetingBoardId());
            UserEntity writer = meetingBoard.getUser();

            UserEntity postAuthor = userRepository.findByUserId(writer.getUserId());
            if (postAuthor != null) {
                ReplyNotificationRequestDto notification = new ReplyNotificationRequestDto();
                notification.setId(UUID.randomUUID().toString());
                notification.setMeetingId(meetingBoard.getMeeting().getMeetingId());
                notification.setMeetingBoardId(dto.getMeetingBoardId());
                notification.setWriterId(postAuthor.getUserId());
                notification.setReplySender(username);
                notification.setReplyContent(authenticationMessage.getReplyContent());
                notification.setType("REPLY");

                messagingTemplate.convertAndSend("/topic/notifications/" + postAuthor.getNickname(), notification);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    @MessageMapping("/chat/message")
    public void notifyChatMessage(@Payload PostChatMessageRequestDto dto) {
        try {
            chatService.postChatMessage(dto);
            ResponseEntity<? super GetSavedMessageResponseDto> savedMessageResponse = chatService.getSavedMessage(dto.getMessageKey());
            ChatMessageEntity savedMessage = ((GetSavedMessageResponseDto) savedMessageResponse.getBody()).getSavedMessage();

            messagingTemplate.convertAndSend("/topic/chat." + dto.getRoomId(),
                    GetChatMessageResponseDto.success(savedMessage));

            ChatMessageEntity chatMessage = chatMessageRepository.findByMessageKey(dto.getMessageKey());
            if (chatMessage == null) {
                System.out.println("Failed to retrieve chat message with messageKey: " + dto.getMessageKey());
                return;
            }

            ChatRoomEntity chatRoom = chatRoomRepository.findByRoomId(chatMessage.getRoomId());
            if (chatRoom == null) {
                System.out.println("Failed to retrieve chat room with roomId: " + chatMessage.getRoomId());
                return;
            }

            List<String> usersInRoom = Arrays.asList(chatRoom.getNickname(), chatRoom.getCreatorNickname());
            for (String receiverId : usersInRoom) {
                if (!receiverId.equals(chatMessage.getSender())) {
                    ChatNotificationRequestDto notification = new ChatNotificationRequestDto();
                    notification.setId(UUID.randomUUID().toString());
                    notification.setChatRoomId(chatMessage.getRoomId());
                    notification.setSenderId(chatMessage.getSender());
                    notification.setMessage(chatMessage.getMessage());
                    notification.setType("CHAT");

                    messagingTemplate.convertAndSend("/topic/notifications/" + receiverId, notification);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    @Getter
    @Setter
    public static class AuthenticationMessage {
        private Long meetingBoardId;
        private String replySender;
        private String replyContent;
        private Long roomId;
        private String messageSender;

    }
}
