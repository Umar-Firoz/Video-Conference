package com.umar.backend.participant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantEventDTO {

    private String type;
    private Long userId;
    private String username;
}