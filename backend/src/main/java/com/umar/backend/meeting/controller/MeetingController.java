package com.umar.backend.meeting.controller;

import com.umar.backend.meeting.dto.CreateMeetingRequestDTO;
import com.umar.backend.meeting.dto.MeetingResponseDTO;
import com.umar.backend.meeting.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping("/create")
    public ResponseEntity<MeetingResponseDTO> createMeeting(@RequestBody CreateMeetingRequestDTO createMeetingRequestDTO) {
        return ResponseEntity.ok(meetingService.createMeeting(createMeetingRequestDTO));
    }

    @GetMapping("/{code}")
    public ResponseEntity<MeetingResponseDTO> getMeeting(@PathVariable String code) {
        return ResponseEntity.ok(meetingService.getMeeting(code));
    }

    @PostMapping("/{code}/end")
    public ResponseEntity<Void> endMeeting(@PathVariable String code) {
        meetingService.endMeeting(code);
        return ResponseEntity.noContent().build();
    }
}