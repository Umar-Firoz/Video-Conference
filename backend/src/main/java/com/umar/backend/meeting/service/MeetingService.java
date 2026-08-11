package com.umar.backend.meeting.service;

import com.umar.backend.common.util.CurrentUser;
import com.umar.backend.common.util.MeetingCodeGenerator;
import com.umar.backend.meeting.dto.CreateMeetingRequestDTO;
import com.umar.backend.meeting.dto.MeetingResponseDTO;
import com.umar.backend.meeting.entity.Meeting;
import com.umar.backend.meeting.repository.MeetingRepository;
import com.umar.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final MeetingCodeGenerator meetingCodeGenerator;
    private final CurrentUser currentUser;
    User getCurrentUser() {
        return currentUser.getCurrentUser();
    }

    public MeetingResponseDTO createMeeting(CreateMeetingRequestDTO createMeetingRequestDTO) {
        Meeting meeting = new Meeting();
        meeting.setTitle(createMeetingRequestDTO.getTitle());
        meeting.setCode(meetingCodeGenerator.generateCode());
        meeting.setCreatedBy(getCurrentUser());
        meeting.setStartTime(LocalDateTime.now());
        meeting.setEndTime(null);
        Meeting savedMeeting = meetingRepository.save(meeting);

        MeetingResponseDTO response = new MeetingResponseDTO();
        response.setId(savedMeeting.getId());
        response.setTitle(savedMeeting.getTitle());
        response.setCode(savedMeeting.getCode());
        response.setCreatedBy(savedMeeting.getCreatedBy().getId());
        response.setStartTime(savedMeeting.getStartTime());
        response.setEndTime(savedMeeting.getEndTime());
        return response;
    }

    public MeetingResponseDTO getMeeting(String code) {

        Meeting meeting = meetingRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        MeetingResponseDTO response = new MeetingResponseDTO();

        response.setId(meeting.getId());
        response.setTitle(meeting.getTitle());
        response.setCode(meeting.getCode());
        response.setCreatedBy(meeting.getCreatedBy().getId());
        response.setStartTime(meeting.getStartTime());
        response.setEndTime(meeting.getEndTime());

        return response;
    }

    public void endMeeting(String code) {

        Meeting meeting = meetingRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        User currentUser = getCurrentUser();
        if (!meeting.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Only the meeting host can end the meeting");
        }
        meeting.setEndTime(LocalDateTime.now());
        meetingRepository.save(meeting);
    }
}
