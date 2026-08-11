package com.umar.backend.webRTC.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebRTCSignalDTO {
    private String type;
    private Long senderId;
    private Long receiverId;
    private String meetingCode;
    private String payload;
}