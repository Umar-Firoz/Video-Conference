package com.umar.backend.participant.dto;

import com.umar.backend.common.enums.ParticipantRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantResponseDTO {

    private Long id;
    private Long userId;
    private String username;
    private ParticipantRole role;
    private LocalDateTime joinedAt;
    private LocalDateTime leftAt;

}