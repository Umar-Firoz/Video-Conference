package com.umar.backend.participant.controller;

import com.umar.backend.participant.dto.ParticipantResponseDTO;
import com.umar.backend.participant.entity.MeetingParticipant;
import com.umar.backend.participant.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting")
public class ParticipantController {
    private final ParticipantService participantService;

    @PostMapping("/{code}/join")
    public ResponseEntity<ParticipantResponseDTO> join(@PathVariable String code, @RequestBody MeetingParticipant meetingParticipant) {
        return ResponseEntity.ok(participantService.join(code));
    }

    @PostMapping("/{code}/leave")
    public ResponseEntity<Void> leave(@PathVariable String code) {
        participantService.leave(code);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{code}/participants")
    public ResponseEntity<List<ParticipantResponseDTO>> getParticipants(@PathVariable String code) {
        return ResponseEntity.ok(participantService.getParticipants(code));
    }
}
