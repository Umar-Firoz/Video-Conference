package com.umar.backend.participant.service;

import com.umar.backend.common.enums.ParticipantRole;
import com.umar.backend.common.util.CurrentUser;
import com.umar.backend.meeting.entity.Meeting;
import com.umar.backend.meeting.repository.MeetingRepository;
import com.umar.backend.participant.dto.ParticipantEventDTO;
import com.umar.backend.participant.dto.ParticipantResponseDTO;
import com.umar.backend.participant.entity.MeetingParticipant;
import com.umar.backend.participant.repository.ParticipantRepository;
import com.umar.backend.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final MeetingRepository meetingRepository;
    private final CurrentUser currentUser;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public ParticipantResponseDTO join(String code) {

        User user = currentUser.getCurrentUser();

        Meeting meeting = meetingRepository.findByCode(code)
                .orElseThrow(() ->
                        new RuntimeException("Meeting not found"));

        if (participantRepository.existsByUserIdAndMeetingId(
                user.getId(),
                meeting.getId())) {

            throw new RuntimeException("User already joined the meeting");
        }

        MeetingParticipant participant = new MeetingParticipant();

        participant.setUser(user);
        participant.setMeeting(meeting);
        participant.setRole(ParticipantRole.PARTICIPANT);
        participant.setJoinedAt(LocalDateTime.now());

        MeetingParticipant saved =
                participantRepository.save(participant);

        ParticipantResponseDTO response =
                new ParticipantResponseDTO();

        response.setId(saved.getId());
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setRole(saved.getRole());
        response.setJoinedAt(saved.getJoinedAt());
        response.setLeftAt(saved.getLeftAt());

        ParticipantEventDTO event = new ParticipantEventDTO(
                "JOINED",
                user.getId(),
                user.getUsername());

        messagingTemplate.convertAndSend("/topic/meeting/" + code + "/participants", event);
        return response;
    }
    public void leave(String code) {

        User user = currentUser.getCurrentUser();

        Meeting meeting = meetingRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        MeetingParticipant participant = participantRepository.findByUserIdAndMeetingId(user.getId(), meeting.getId())
                        .orElseThrow(() -> new RuntimeException("User is not a participant of this meeting"));
        participant.setLeftAt(LocalDateTime.now());
        participantRepository.save(participant);

        ParticipantEventDTO event = new ParticipantEventDTO(
                "LEFT",
                user.getId(),
                user.getUsername());

        messagingTemplate.convertAndSend("/topic/meeting/" + code + "/participants", event);
    }

    public List<ParticipantResponseDTO> getParticipants(String code) {

        Meeting meeting = meetingRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Meeting not found"));

        List<MeetingParticipant> participants = participantRepository.findByMeetingId(meeting.getId());



        return participants.stream()
                .map(participant -> {
                    ParticipantResponseDTO response =
                            new ParticipantResponseDTO();
                    response.setId(participant.getId());
                    response.setUserId(participant.getUser().getId());
                    response.setUsername(participant.getUser().getUsername());
                    response.setRole(participant.getRole());
                    response.setJoinedAt(participant.getJoinedAt());
                    response.setLeftAt(participant.getLeftAt());
                    return response;
                })
                .toList();
    }
}



