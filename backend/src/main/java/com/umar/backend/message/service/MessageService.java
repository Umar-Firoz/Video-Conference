package com.umar.backend.message.service;

import com.umar.backend.common.util.CurrentUser;
import com.umar.backend.meeting.entity.Meeting;
import com.umar.backend.meeting.repository.MeetingRepository;
import com.umar.backend.message.dto.MessageResponseDTO;
import com.umar.backend.message.dto.SendMessageRequestDTO;
import com.umar.backend.message.entity.Message;
import com.umar.backend.message.repository.MessageRepository;
import com.umar.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MeetingRepository meetingRepository;
    private final CurrentUser currentUser;

    public MessageResponseDTO sendMessage(
            String code,
            SendMessageRequestDTO request) {

        User user = currentUser.getCurrentUser();

        Meeting meeting = meetingRepository.findByCode(code)
                .orElseThrow(() ->
                        new RuntimeException("Meeting not found"));

        Message message = new Message();

        message.setMeeting(meeting);
        message.setSender(user);
        message.setContent(request.getContent());
        message.setSentAt(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);

        MessageResponseDTO response = new MessageResponseDTO();

        response.setId(savedMessage.getId());
        response.setSenderId(user.getId());
        response.setSenderName(user.getUsername());
        response.setContent(savedMessage.getContent());
        response.setSentAt(savedMessage.getSentAt());

        return response;
    }
}