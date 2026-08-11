package com.umar.backend.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingResponseDTO {

    private Long id;
    private String title;
    private String code;
    private Long createdBy;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}